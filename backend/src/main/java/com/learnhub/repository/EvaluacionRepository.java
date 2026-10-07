package com.learnhub.repository;

import com.learnhub.entity.Evaluacion;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository for Evaluacion entities.
 */
public interface EvaluacionRepository extends JpaRepository<Evaluacion, Long> {
}
