package com.learnhub.controller;

import com.learnhub.dto.ModuloRequest;
import com.learnhub.dto.ModuloResponse;
import com.learnhub.service.ModuloService;
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
@RequestMapping("/api/modulos")
public class ModuloController {

    private final ModuloService moduloService;

    public ModuloController(ModuloService moduloService) {
        this.moduloService = moduloService;
    }

    @PostMapping
    public ResponseEntity<ModuloResponse> crear(
            @Valid @RequestBody ModuloRequest request) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(moduloService.crearModulo(request));
    }

    @GetMapping
    public ResponseEntity<List<ModuloResponse>> listar() {
        return ResponseEntity.ok(moduloService.listarModulos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ModuloResponse> buscar(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                moduloService.buscarModuloPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ModuloResponse> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody ModuloRequest request) {

        return ResponseEntity.ok(
                moduloService.actualizarModulo(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {

        moduloService.eliminarModulo(id);

        return ResponseEntity.noContent().build();
    }
}