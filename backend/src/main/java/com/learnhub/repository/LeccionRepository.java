package com.learnhub.repository;

import com.learnhub.entity.Leccion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LeccionRepository extends JpaRepository<Leccion, Long> {
}
