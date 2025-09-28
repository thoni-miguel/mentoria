package com.mentoria.platform.controller;

import com.mentoria.platform.domain.Activity;
import com.mentoria.platform.domain.Subject;
import com.mentoria.platform.dto.ActivityRequest;
import com.mentoria.platform.dto.ActivityResponse;
import com.mentoria.platform.repository.ActivityRepository;
import com.mentoria.platform.repository.SubjectRepository;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/activities")
public class ActivityController {

    private final ActivityRepository activityRepo;
    private final SubjectRepository subjectRepo;

    public ActivityController(ActivityRepository activityRepo, SubjectRepository subjectRepo) {
        this.activityRepo = activityRepo;
        this.subjectRepo = subjectRepo;
    }

    @PostMapping
    public ResponseEntity<ActivityResponse> create(@Valid @RequestBody ActivityRequest req) {
        Subject subject = subjectRepo.findById(req.subjectId())
                .orElseThrow(() -> new IllegalArgumentException("Subject não encontrado"));

        Activity entity = Activity.builder()
                .title(req.title())
                .subject(subject)
                .startAt(req.startAt())
                .endAt(req.endAt())
                .allDay(req.allDay() != null ? req.allDay() : false)
                .isCompleted(false)
                .build();

        Activity saved = activityRepo.save(entity);
        ActivityResponse body = toResponse(saved);
        return ResponseEntity
                .created(URI.create("/api/activities/" + saved.getId()))
                .body(body);
    }

    @GetMapping
    public List<ActivityResponse> list() {
        return activityRepo.findAll().stream().map(this::toResponse).toList();
    }

    private ActivityResponse toResponse(Activity a) {
        return new ActivityResponse(
                a.getId(),
                a.getTitle(),
                a.getSubject().getId(),
                a.getSubject().getCode(),
                a.getSubject().getLabel(),
                a.getStartAt(),
                a.getEndAt(),
                a.isAllDay(),
                a.isCompleted()
        );
    }
}