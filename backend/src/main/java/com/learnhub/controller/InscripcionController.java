package com.learnhub.controller;

import com.learnhub.dto.InscripcionRequest;
import com.learnhub.dto.InscripcionResponse;
import com.learnhub.service.InscripcionService;
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
 * Provides REST endpoints for enrollment management.
 */
@RestController
@RequestMapping("/api/inscripciones")
public class InscripcionController {

    private final InscripcionService inscripcionService;

    /**
     * Creates the enrollment controller with its required service.
     *
     * @param inscripcionService service used to manage enrollments
     */
    public InscripcionController(
            InscripcionService inscripcionService) {
        this.inscripcionService = inscripcionService;
    }

    /**
     * Creates a new enrollment.
     *
     * @param request enrollment creation data
     * @return created enrollment
     */
    @PostMapping
    public ResponseEntity<InscripcionResponse> crearInscripcion(
            @Valid @RequestBody InscripcionRequest request) {

        InscripcionResponse response =
                inscripcionService.crearInscripcion(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    /**
     * Retrieves all enrollments.
     *
     * @return list of enrollments
     */
    @GetMapping
    public ResponseEntity<List<InscripcionResponse>> listarInscripciones() {

        return ResponseEntity.ok(
                inscripcionService.listarInscripciones());
    }

    /**
     * Retrieves an enrollment by its identifier.
     *
     * @param id enrollment identifier
     * @return enrollment data
     */
    @GetMapping("/{id}")
    public ResponseEntity<InscripcionResponse> buscarInscripcionPorId(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                inscripcionService.buscarInscripcionPorId(id));
    }

    /**
     * Updates an existing enrollment.
     *
     * @param id enrollment identifier
     * @param request updated enrollment data
     * @return updated enrollment
     */
    @PutMapping("/{id}")
    public ResponseEntity<InscripcionResponse> actualizarInscripcion(
            @PathVariable Long id,
            @Valid @RequestBody InscripcionRequest request) {

        return ResponseEntity.ok(
                inscripcionService.actualizarInscripcion(id, request));
    }

    /**
     * Deletes an enrollment.
     *
     * @param id enrollment identifier
     * @return empty response
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarInscripcion(
            @PathVariable Long id) {

        inscripcionService.eliminarInscripcion(id);

        return ResponseEntity.noContent().build();
    }
}