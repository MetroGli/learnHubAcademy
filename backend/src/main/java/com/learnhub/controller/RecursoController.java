package com.learnhub.controller;

import com.learnhub.dto.RecursoRequest;
import com.learnhub.dto.RecursoResponse;
import com.learnhub.service.RecursoService;
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
 * Provides REST endpoints for resource management.
 */
@RestController
@RequestMapping("/api/recursos")
public class RecursoController {

    private final RecursoService recursoService;

    /**
     * Creates the resource controller.
     *
     * @param recursoService resource service
     */
    public RecursoController(RecursoService recursoService) {
        this.recursoService = recursoService;
    }

    /**
     * Creates a resource.
     *
     * @param request resource data
     * @return created resource
     */
    @PostMapping
    public ResponseEntity<RecursoResponse> crear(
            @Valid @RequestBody RecursoRequest request) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(recursoService.crearRecurso(request));
    }

    /**
     * Lists resources.
     *
     * @return list of resources
     */
    @GetMapping
    public ResponseEntity<List<RecursoResponse>> listar() {
        return ResponseEntity.ok(recursoService.listarRecursos());
    }

    /**
     * Gets a resource by identifier.
     *
     * @param id resource identifier
     * @return resource
     */
    @GetMapping("/{id}")
    public ResponseEntity<RecursoResponse> buscar(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                recursoService.buscarRecursoPorId(id));
    }

    /**
     * Updates a resource.
     *
     * @param id resource identifier
     * @param request updated resource data
     * @return updated resource
     */
    @PutMapping("/{id}")
    public ResponseEntity<RecursoResponse> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody RecursoRequest request) {

        return ResponseEntity.ok(
                recursoService.actualizarRecurso(id, request));
    }

    /**
     * Deletes a resource.
     *
     * @param id resource identifier
     * @return empty response
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {

        recursoService.eliminarRecurso(id);

        return ResponseEntity.noContent().build();
    }
}