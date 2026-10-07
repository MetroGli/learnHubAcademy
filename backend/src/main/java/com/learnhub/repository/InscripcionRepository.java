package com.learnhub.repository;

import com.learnhub.entity.Inscripcion;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository for Inscripcion entities.
 */
public interface InscripcionRepository extends JpaRepository<Inscripcion, Long> {
}
