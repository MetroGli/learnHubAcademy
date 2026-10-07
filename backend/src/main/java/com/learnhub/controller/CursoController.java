package com.learnhub.controller;

import com.learnhub.dto.CursoRequest;
import com.learnhub.dto.CursoResponse;
import com.learnhub.service.CursoService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for course management.
 */
@RestController
@RequestMapping("/api/cursos")
public class CursoController {

    private final CursoService cursoService;

    /**
     * Creates the course controller.
     *
     * @param cursoService service used to manage courses
     */
    public CursoController(CursoService cursoService) {
        this.cursoService = cursoService;
    }

    /**
     * Creates a new course.
     *
     * @param request course creation data
     * @return created course
     */
    @PostMapping
    public ResponseEntity<CursoResponse> crearCurso(
            @Valid @RequestBody CursoRequest request) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(cursoService.crearCurso(request));
    }

    /**
     * Retrieves all courses.
     *
     * @return list of courses
     */
    @GetMapping
    public ResponseEntity<List<CursoResponse>> listarCursos() {
        return ResponseEntity.ok(cursoService.listarCursos());
    }

    /**
     * Retrieves a course by its identifier.
     *
     * @param id course identifier
     * @return course response
     */
    @GetMapping("/{id}")
    public ResponseEntity<CursoResponse> buscarCursoPorId(
            @PathVariable Long id) {

        return ResponseEntity.ok(cursoService.buscarCursoPorId(id));
    }

    /**
     * Updates an existing course.
     *
     * @param id course identifier
     * @param request updated course data
     * @return updated course
     */
    @PutMapping("/{id}")
    public ResponseEntity<CursoResponse> actualizarCurso(
            @PathVariable Long id,
            @Valid @RequestBody CursoRequest request) {

        return ResponseEntity.ok(
                cursoService.actualizarCurso(id, request));
    }

    /**
     * Deletes an existing course.
     *
     * @param id course identifier
     * @return empty response
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarCurso(
            @PathVariable Long id) {

        cursoService.eliminarCurso(id);
        return ResponseEntity.noContent().build();
    }
}