package com.learnhub.controller;

import com.learnhub.dto.RespuestaRequest;
import com.learnhub.dto.RespuestaResponse;
import com.learnhub.service.RespuestaService;
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
 * Provides REST endpoints for answer management.
 */
@RestController
@RequestMapping("/api/respuestas")
public class RespuestaController {

    private final RespuestaService respuestaService;

    /**
     * Creates the answer controller.
     *
     * @param respuestaService answer service
     */
    public RespuestaController(RespuestaService respuestaService) {
        this.respuestaService = respuestaService;
    }

    /**
     * Creates an answer.
     *
     * @param request answer data
     * @return created answer
     */
    @PostMapping
    public ResponseEntity<RespuestaResponse> crear(
            @Valid @RequestBody RespuestaRequest request) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(respuestaService.crearRespuesta(request));
    }

    /**
     * Lists answers.
     *
     * @return list of answers
     */
    @GetMapping
    public ResponseEntity<List<RespuestaResponse>> listar() {
        return ResponseEntity.ok(respuestaService.listarRespuestas());
    }

    /**
     * Gets an answer by identifier.
     *
     * @param id answer identifier
     * @return answer
     */
    @GetMapping("/{id}")
    public ResponseEntity<RespuestaResponse> buscar(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                respuestaService.buscarRespuestaPorId(id));
    }

    /**
     * Updates an answer.
     *
     * @param id answer identifier
     * @param request updated answer data
     * @return updated answer
     */
    @PutMapping("/{id}")
    public ResponseEntity<RespuestaResponse> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody RespuestaRequest request) {

        return ResponseEntity.ok(
                respuestaService.actualizarRespuesta(id, request));
    }

    /**
     * Deletes an answer.
     *
     * @param id answer identifier
     * @return empty response
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {

        respuestaService.eliminarRespuesta(id);

        return ResponseEntity.noContent().build();
    }
}