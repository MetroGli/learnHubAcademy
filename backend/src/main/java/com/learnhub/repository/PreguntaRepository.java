package com.learnhub.repository;

import com.learnhub.entity.Pregunta;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository for Pregunta entities.
 */
public interface PreguntaRepository extends JpaRepository<Pregunta, Long> {
}
