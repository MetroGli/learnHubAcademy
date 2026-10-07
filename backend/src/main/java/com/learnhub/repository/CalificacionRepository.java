package com.learnhub.repository;

import com.learnhub.entity.Calificacion;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository for Calificacion entities.
 */
public interface CalificacionRepository extends JpaRepository<Calificacion, Long> {
}
