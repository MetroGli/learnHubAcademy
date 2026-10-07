package com.learnhub.controller;

import com.learnhub.dto.PreguntaRequest;
import com.learnhub.dto.PreguntaResponse;
import com.learnhub.service.PreguntaService;
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
 * Provides REST endpoints for question management.
 */
@RestController
@RequestMapping("/api/preguntas")
public class PreguntaController {

    private final PreguntaService preguntaService;

    /**
     * Creates the question controller.
     *
     * @param preguntaService question service
     */
    public PreguntaController(PreguntaService preguntaService) {
        this.preguntaService = preguntaService;
    }

    /**
     * Creates a question.
     *
     * @param request question data
     * @return created question
     */
    @PostMapping
    public ResponseEntity<PreguntaResponse> crear(
            @Valid @RequestBody PreguntaRequest request) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(preguntaService.crearPregunta(request));
    }

    /**
     * Lists questions.
     *
     * @return list of questions
     */
    @GetMapping
    public ResponseEntity<List<PreguntaResponse>> listar() {
        return ResponseEntity.ok(preguntaService.listarPreguntas());
    }

    /**
     * Gets a question by identifier.
     *
     * @param id question identifier
     * @return question
     */
    @GetMapping("/{id}")
    public ResponseEntity<PreguntaResponse> buscar(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                preguntaService.buscarPreguntaPorId(id));
    }

    /**
     * Updates a question.
     *
     * @param id question identifier
     * @param request updated question data
     * @return updated question
     */
    @PutMapping("/{id}")
    public ResponseEntity<PreguntaResponse> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody PreguntaRequest request) {

        return ResponseEntity.ok(
                preguntaService.actualizarPregunta(id, request));
    }

    /**
     * Deletes a question.
     *
     * @param id question identifier
     * @return empty response
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {

        preguntaService.eliminarPregunta(id);

        return ResponseEntity.noContent().build();
    }
}