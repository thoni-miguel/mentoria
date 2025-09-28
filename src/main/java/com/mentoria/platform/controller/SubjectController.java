package com.mentoria.platform.controller;

import com.mentoria.platform.domain.Subject;
import com.mentoria.platform.repository.SubjectRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/subjects")
public class SubjectController {

    private final SubjectRepository repo;
    public SubjectController(SubjectRepository repo) { this.repo = repo; }

    @GetMapping
    public List<Subject> list() {
        return repo.findAll();
    }
}