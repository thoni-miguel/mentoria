package com.mentoria.platform.repository;

import com.mentoria.platform.domain.Subject;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SubjectRepository extends JpaRepository<Subject, Long> {
    Optional<Subject> findByCode(String code);
    boolean existsByCode(String code);
}