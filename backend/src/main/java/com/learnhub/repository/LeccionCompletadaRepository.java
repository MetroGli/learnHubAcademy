package com.learnhub.repository;

import com.learnhub.entity.LeccionCompletada;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LeccionCompletadaRepository extends JpaRepository<LeccionCompletada, Long> {

    List<LeccionCompletada> findByUsuarioIdAndLeccionModuloCursoId(Long usuarioId, Long cursoId);

    Optional<LeccionCompletada> findByUsuarioIdAndLeccionId(Long usuarioId, Long leccionId);
}
