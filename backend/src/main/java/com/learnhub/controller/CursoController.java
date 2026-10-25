package com.learnhub.controller;

import com.learnhub.dto.CursoRequest;
import com.learnhub.dto.CursoResponse;
import com.learnhub.service.CursoService;
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
@RequestMapping("/api/cursos")
public class CursoController {

    private final CursoService cursoService;

    public CursoController(CursoService cursoService) {
        this.cursoService = cursoService;
    }

    @PostMapping
    public ResponseEntity<CursoResponse> crearCurso(
            @Valid @RequestBody CursoRequest request) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(cursoService.crearCurso(request));
    }

    @GetMapping
    public ResponseEntity<List<CursoResponse>> listarCursos() {
        return ResponseEntity.ok(cursoService.listarCursos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CursoResponse> buscarCursoPorId(
            @PathVariable Long id) {

        return ResponseEntity.ok(cursoService.buscarCursoPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CursoResponse> actualizarCurso(
            @PathVariable Long id,
            @Valid @RequestBody CursoRequest request) {

        return ResponseEntity.ok(
                cursoService.actualizarCurso(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarCurso(
            @PathVariable Long id) {

        cursoService.eliminarCurso(id);
        return ResponseEntity.noContent().build();
    }
}