package com.learnhub.repository;

import com.learnhub.entity.Inscripcion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InscripcionRepository extends JpaRepository<Inscripcion, Long> {

    boolean existsByUsuarioIdAndCursoId(Long usuarioId, Long cursoId);

    boolean existsByUsuarioIdAndCursoIdAndIdNot(Long usuarioId, Long cursoId, Long id);

    boolean existsByUsuarioIdAndCursoIdAndActivaTrue(Long usuarioId, Long cursoId);
}
