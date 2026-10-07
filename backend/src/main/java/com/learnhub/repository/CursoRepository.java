package com.learnhub.repository;

import com.learnhub.entity.Curso;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository for Curso entities.
 */
public interface CursoRepository extends JpaRepository<Curso, Long> {
}
