package com.learnhub.controller;

import com.learnhub.dto.AvanceCursoResponse;
import com.learnhub.dto.PresentarEvaluacionRequest;
import com.learnhub.dto.ResultadoEvaluacionResponse;
import com.learnhub.security.UsuarioActual;
import com.learnhub.service.MiAvanceService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Avance del usuario que inició sesión: lecciones completadas y evaluaciones.
 */
@RestController
@RequestMapping("/api/mi-avance")
public class MiAvanceController {

    private final MiAvanceService miAvanceService;
    private final UsuarioActual usuarioActual;

    public MiAvanceController(MiAvanceService miAvanceService, UsuarioActual usuarioActual) {
        this.miAvanceService = miAvanceService;
        this.usuarioActual = usuarioActual;
    }

    @GetMapping("/cursos/{cursoId}")
    public ResponseEntity<AvanceCursoResponse> avance(@PathVariable Long cursoId) {
        return ResponseEntity.ok(miAvanceService.avance(usuarioActual.obtener(), cursoId));
    }

    @PostMapping("/lecciones/{leccionId}")
    public ResponseEntity<AvanceCursoResponse> completar(@PathVariable Long leccionId) {
        return ResponseEntity.ok(
                miAvanceService.marcarLeccion(usuarioActual.obtener(), leccionId, true));
    }

    @DeleteMapping("/lecciones/{leccionId}")
    public ResponseEntity<AvanceCursoResponse> desmarcar(@PathVariable Long leccionId) {
        return ResponseEntity.ok(
                miAvanceService.marcarLeccion(usuarioActual.obtener(), leccionId, false));
    }

    @PostMapping("/evaluaciones/{evaluacionId}")
    public ResponseEntity<ResultadoEvaluacionResponse> presentar(
            @PathVariable Long evaluacionId,
            @Valid @RequestBody PresentarEvaluacionRequest request) {

        return ResponseEntity.ok(miAvanceService.presentar(
                usuarioActual.obtener(), evaluacionId, request.respuestas()));
    }
}
