package com.learnhub.repository;

import com.learnhub.entity.Progreso;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProgresoRepository extends JpaRepository<Progreso, Long> {

    Optional<Progreso> findByUsuarioIdAndCursoId(Long usuarioId, Long cursoId);
}
