package com.learnhub.controller;

import com.learnhub.dto.LeccionRequest;
import com.learnhub.dto.LeccionResponse;
import com.learnhub.service.LeccionService;
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
@RequestMapping("/api/lecciones")
public class LeccionController {

    private final LeccionService leccionService;

    public LeccionController(LeccionService leccionService) {
        this.leccionService = leccionService;
    }

    @PostMapping
    public ResponseEntity<LeccionResponse> crear(
            @Valid @RequestBody LeccionRequest request) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(leccionService.crearLeccion(request));
    }

    @GetMapping
    public ResponseEntity<List<LeccionResponse>> listar() {
        return ResponseEntity.ok(leccionService.listarLecciones());
    }

    @GetMapping("/{id}")
    public ResponseEntity<LeccionResponse> buscar(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                leccionService.buscarLeccionPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<LeccionResponse> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody LeccionRequest request) {

        return ResponseEntity.ok(
                leccionService.actualizarLeccion(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {

        leccionService.eliminarLeccion(id);

        return ResponseEntity.noContent().build();
    }
}