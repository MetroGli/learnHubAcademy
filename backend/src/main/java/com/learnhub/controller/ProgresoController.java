package com.learnhub.controller;

import com.learnhub.dto.ProgresoRequest;
import com.learnhub.dto.ProgresoResponse;
import com.learnhub.service.ProgresoService;
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
@RequestMapping("/api/progresos")
public class ProgresoController {

    private final ProgresoService progresoService;

    public ProgresoController(ProgresoService progresoService) {
        this.progresoService = progresoService;
    }

    @PostMapping
    public ResponseEntity<ProgresoResponse> crear(
            @Valid @RequestBody ProgresoRequest request) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(progresoService.crearProgreso(request));
    }

    @GetMapping
    public ResponseEntity<List<ProgresoResponse>> listar() {
        return ResponseEntity.ok(progresoService.listarProgresos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProgresoResponse> buscar(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                progresoService.buscarProgresoPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProgresoResponse> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody ProgresoRequest request) {

        return ResponseEntity.ok(
                progresoService.actualizarProgreso(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {

        progresoService.eliminarProgreso(id);

        return ResponseEntity.noContent().build();
    }
}