package com.mentoria.platform.dto;

import jakarta.validation.constraints.*;
import java.time.OffsetDateTime;

public record ActivityRequest(
        @NotNull Long subjectId,
        @NotBlank @Size(max = 140) String title,
        @NotNull OffsetDateTime startAt,
        @NotNull OffsetDateTime endAt,
        Boolean allDay
) {}