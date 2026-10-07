package com.learnhub.repository;

import com.learnhub.entity.Leccion;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository for Leccion entities.
 */
public interface LeccionRepository extends JpaRepository<Leccion, Long> {
}
