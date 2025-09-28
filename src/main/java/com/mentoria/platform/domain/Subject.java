package com.mentoria.platform.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "subjects", uniqueConstraints = {
        @UniqueConstraint(name = "uk_subject_code", columnNames = "code")
})
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Subject {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 40)
    private String code;   // ex.: PEDIATRIA, GO, PREVENTIVA, CLINICA, CIRURGIA

    @Column(nullable = false, length = 80)
    private String label;  // ex.: Pediatria, Ginecologia e Obstetrícia
}