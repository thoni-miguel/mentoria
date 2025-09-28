package com.mentoria.platform.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.OffsetDateTime;

@Entity
@Table(name = "activities")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Activity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 140)
    private String title;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "subject_id", nullable = false)
    private Subject subject;

    @Column(name = "start_at", nullable = false)
    private OffsetDateTime startAt;     // ISO-8601 vindo do front (ideal com timezone)

    @Column(name = "end_at", nullable = false)
    private OffsetDateTime endAt;

    @Column(name = "all_day", nullable = false)
    private boolean allDay = false;

    @Column(name = "is_completed", nullable = false)
    private boolean isCompleted = false;
}