package com.mentoria.platform.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final OAuth2UserService<OidcUserRequest, OidcUser> customOidcUserService;

    public SecurityConfig(OAuth2UserService<OidcUserRequest, OidcUser> customOidcUserService) {
        this.customOidcUserService = customOidcUserService;
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/", "/login", "/error").permitAll()
                        .anyRequest().authenticated()
                )
                .oauth2Login(o -> o.userInfoEndpoint(u ->
                        u.oidcUserService(customOidcUserService)))
                .logout(logout -> logout.logoutSuccessUrl("/").permitAll());
        return http.build();
    }
}