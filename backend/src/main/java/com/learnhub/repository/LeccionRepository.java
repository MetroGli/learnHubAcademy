package com.learnhub.repository;

import com.learnhub.entity.Leccion;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LeccionRepository extends JpaRepository<Leccion, Long> {

    List<Leccion> findByModuloCursoId(Long cursoId);
}
