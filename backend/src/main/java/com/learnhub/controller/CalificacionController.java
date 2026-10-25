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

@RestController
@RequestMapping("/api/calificaciones")
public class CalificacionController {

    private final CalificacionService calificacionService;

    public CalificacionController(
            CalificacionService calificacionService) {
        this.calificacionService = calificacionService;
    }

    @PostMapping
    public ResponseEntity<CalificacionResponse> crear(
            @Valid @RequestBody CalificacionRequest request) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(calificacionService.crearCalificacion(request));
    }

    @GetMapping
    public ResponseEntity<List<CalificacionResponse>> listar() {
        return ResponseEntity.ok(
                calificacionService.listarCalificaciones());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CalificacionResponse> buscar(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                calificacionService.buscarCalificacionPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CalificacionResponse> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody CalificacionRequest request) {

        return ResponseEntity.ok(
                calificacionService.actualizarCalificacion(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {

        calificacionService.eliminarCalificacion(id);

        return ResponseEntity.noContent().build();
    }
}