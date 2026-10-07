package com.learnhub.controller;

import com.learnhub.dto.EvaluacionRequest;
import com.learnhub.dto.EvaluacionResponse;
import com.learnhub.service.EvaluacionService;
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
 * Provides REST endpoints for evaluation management.
 */
@RestController
@RequestMapping("/api/evaluaciones")
public class EvaluacionController {

    private final EvaluacionService evaluacionService;

    /**
     * Creates the evaluation controller.
     *
     * @param evaluacionService evaluation service
     */
    public EvaluacionController(
            EvaluacionService evaluacionService) {
        this.evaluacionService = evaluacionService;
    }

    /**
     * Creates an evaluation.
     *
     * @param request evaluation data
     * @return created evaluation
     */
    @PostMapping
    public ResponseEntity<EvaluacionResponse> crear(
            @Valid @RequestBody EvaluacionRequest request) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(evaluacionService.crearEvaluacion(request));
    }

    /**
     * Lists evaluations.
     *
     * @return list of evaluations
     */
    @GetMapping
    public ResponseEntity<List<EvaluacionResponse>> listar() {
        return ResponseEntity.ok(
                evaluacionService.listarEvaluaciones());
    }

    /**
     * Gets an evaluation by identifier.
     *
     * @param id evaluation identifier
     * @return evaluation
     */
    @GetMapping("/{id}")
    public ResponseEntity<EvaluacionResponse> buscar(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                evaluacionService.buscarEvaluacionPorId(id));
    }

    /**
     * Updates an evaluation.
     *
     * @param id evaluation identifier
     * @param request updated evaluation data
     * @return updated evaluation
     */
    @PutMapping("/{id}")
    public ResponseEntity<EvaluacionResponse> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody EvaluacionRequest request) {

        return ResponseEntity.ok(
                evaluacionService.actualizarEvaluacion(id, request));
    }

    /**
     * Deletes an evaluation.
     *
     * @param id evaluation identifier
     * @return empty response
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {

        evaluacionService.eliminarEvaluacion(id);

        return ResponseEntity.noContent().build();
    }
}