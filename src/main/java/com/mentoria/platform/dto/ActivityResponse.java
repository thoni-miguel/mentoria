package com.mentoria.platform.dto;

import java.time.OffsetDateTime;

public record ActivityResponse(
        Long id,
        String title,
        Long subjectId,
        String subjectCode,
        String subjectLabel,
        OffsetDateTime startAt,
        OffsetDateTime endAt,
        boolean allDay,
        boolean isCompleted
) {}