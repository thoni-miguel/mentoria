package com.mentoria.platform.config;

import com.mentoria.platform.domain.Subject;
import com.mentoria.platform.repository.SubjectRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SubjectSeeder {

    @Bean
    CommandLineRunner seedSubjects(SubjectRepository repo) {
        return args -> {
            upsert(repo, "PEDIATRIA", "Pediatria");
            upsert(repo, "GO", "Ginecologia e Obstetrícia");
            upsert(repo, "PREVENTIVA", "Preventiva");
            upsert(repo, "CLINICA", "Clínica");
            upsert(repo, "CIRURGIA", "Cirurgia");
        };
    }

    private void upsert(SubjectRepository repo, String code, String label) {
        repo.findByCode(code).ifPresentOrElse(
                s -> { if (!label.equals(s.getLabel())) { s.setLabel(label); repo.save(s); } },
                () -> repo.save(Subject.builder().code(code).label(label).build())
        );
    }
}