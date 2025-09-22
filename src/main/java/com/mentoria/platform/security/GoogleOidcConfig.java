package com.mentoria.platform.security;

import com.mentoria.platform.user.User;
import com.mentoria.platform.user.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;

import java.time.Instant;
import java.util.Set;

@Configuration
public class GoogleOidcConfig {

    @Bean
    public org.springframework.security.oauth2.client.userinfo.OAuth2UserService<OidcUserRequest, OidcUser>
    customOidcUserService(UserRepository users) {
        var delegate = new OidcUserService();

        return (OidcUserRequest req) -> {
            OidcUser oidc = delegate.loadUser(req);

            String email = oidc.getEmail();
            boolean verified = Boolean.TRUE.equals(oidc.getEmailVerified());
            if (email == null || !verified || !email.toLowerCase().endsWith("@gmail.com")) {
                throw new OAuth2AuthenticationException(new OAuth2Error("invalid_user"),
                        "Login permitido apenas para emails @gmail.com verificados");
            }

            String name = oidc.getFullName();
            if ((name == null || name.isBlank())) {
                var given = String.valueOf(oidc.getGivenName() == null ? "" : oidc.getGivenName());
                var family = String.valueOf(oidc.getFamilyName() == null ? "" : oidc.getFamilyName());
                name = (given + " " + family).trim();
                if (name.isBlank()) name = email; // fallback
            }

            String picture = oidc.getPicture();
            String emailLower = email.toLowerCase();

            // upsert
            String finalName = name;
            var user = users.findByEmail(emailLower)
                    .map(u -> {
                        u.setName(finalName);
                        u.setPicture(picture);
                        u.setLastLoginAt(Instant.now());
                        return u;
                    })
                    .orElseGet(() -> User.builder()
                            .email(emailLower)
                            .name(finalName)
                            .picture(picture)
                            .lastLoginAt(Instant.now())
                            .build());

            users.save(user);

            var authorities = Set.of(new SimpleGrantedAuthority("ROLE_USER"));
            // mantém ID token e userinfo originais
            return new DefaultOidcUser(authorities, oidc.getIdToken(), oidc.getUserInfo());
        };
    }
}