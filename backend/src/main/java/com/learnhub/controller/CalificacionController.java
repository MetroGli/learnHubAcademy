package com.learnhub.controller;

import com.learnhub.dto.CalificacionRequest;
import com.learnhub.dto.CalificacionResponse;
import com.learnhub.service.CalificacionService;
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
 * Provides REST endpoints for grade management.
 */
@RestController
@RequestMapping("/api/calificaciones")
public class CalificacionController {

    private final CalificacionService calificacionService;

    /**
     * Creates the grade controller.
     *
     * @param calificacionService grade service
     */
    public CalificacionController(
            CalificacionService calificacionService) {
        this.calificacionService = calificacionService;
    }

    /**
     * Creates a grade.
     *
     * @param request grade data
     * @return created grade
     */
    @PostMapping
    public ResponseEntity<CalificacionResponse> crear(
            @Valid @RequestBody CalificacionRequest request) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(calificacionService.crearCalificacion(request));
    }

    /**
     * Lists all grades.
     *
     * @return list of grades
     */
    @GetMapping
    public ResponseEntity<List<CalificacionResponse>> listar() {
        return ResponseEntity.ok(
                calificacionService.listarCalificaciones());
    }

    /**
     * Gets a grade by identifier.
     *
     * @param id grade identifier
     * @return grade
     */
    @GetMapping("/{id}")
    public ResponseEntity<CalificacionResponse> buscar(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                calificacionService.buscarCalificacionPorId(id));
    }

    /**
     * Updates a grade.
     *
     * @param id grade identifier
     * @param request updated grade data
     * @return updated grade
     */
    @PutMapping("/{id}")
    public ResponseEntity<CalificacionResponse> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody CalificacionRequest request) {

        return ResponseEntity.ok(
                calificacionService.actualizarCalificacion(id, request));
    }

    /**
     * Deletes a grade.
     *
     * @param id grade identifier
     * @return empty response
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {

        calificacionService.eliminarCalificacion(id);

        return ResponseEntity.noContent().build();
    }
}