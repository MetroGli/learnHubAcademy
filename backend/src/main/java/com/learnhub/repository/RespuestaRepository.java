package com.learnhub.repository;

import com.learnhub.entity.Respuesta;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository for Respuesta entities.
 */
public interface RespuestaRepository extends JpaRepository<Respuesta, Long> {
}
