package com.learnhub.repository;

import com.learnhub.entity.Recurso;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository for Recurso entities.
 */
public interface RecursoRepository extends JpaRepository<Recurso, Long> {
}
