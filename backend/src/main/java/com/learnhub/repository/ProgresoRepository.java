package com.learnhub.repository;

import com.learnhub.entity.Progreso;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository for Progreso entities.
 */
public interface ProgresoRepository extends JpaRepository<Progreso, Long> {
}
