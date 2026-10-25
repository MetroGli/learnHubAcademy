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

@RestController
@RequestMapping("/api/recursos")
public class RecursoController {

    private final RecursoService recursoService;

    public RecursoController(RecursoService recursoService) {
        this.recursoService = recursoService;
    }

    @PostMapping
    public ResponseEntity<RecursoResponse> crear(
            @Valid @RequestBody RecursoRequest request) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(recursoService.crearRecurso(request));
    }

    @GetMapping
    public ResponseEntity<List<RecursoResponse>> listar() {
        return ResponseEntity.ok(recursoService.listarRecursos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RecursoResponse> buscar(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                recursoService.buscarRecursoPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<RecursoResponse> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody RecursoRequest request) {

        return ResponseEntity.ok(
                recursoService.actualizarRecurso(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {

        recursoService.eliminarRecurso(id);

        return ResponseEntity.noContent().build();
    }
}