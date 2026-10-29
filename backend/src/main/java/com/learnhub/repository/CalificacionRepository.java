package com.learnhub.repository;

import com.learnhub.entity.Calificacion;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CalificacionRepository extends JpaRepository<Calificacion, Long> {

    Optional<Calificacion> findByUsuarioIdAndEvaluacionId(Long usuarioId, Long evaluacionId);
}
