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

/**
 * Provides REST endpoints for module management.
 */
@RestController
@RequestMapping("/api/modulos")
public class ModuloController {

    private final ModuloService moduloService;

    /**
     * Creates the module controller.
     *
     * @param moduloService module service
     */
    public ModuloController(ModuloService moduloService) {
        this.moduloService = moduloService;
    }

    /**
     * Creates a module.
     *
     * @param request module data
     * @return created module
     */
    @PostMapping
    public ResponseEntity<ModuloResponse> crear(
            @Valid @RequestBody ModuloRequest request) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(moduloService.crearModulo(request));
    }

    /**
     * Lists modules.
     *
     * @return list of modules
     */
    @GetMapping
    public ResponseEntity<List<ModuloResponse>> listar() {
        return ResponseEntity.ok(moduloService.listarModulos());
    }

    /**
     * Gets a module by identifier.
     *
     * @param id module identifier
     * @return module
     */
    @GetMapping("/{id}")
    public ResponseEntity<ModuloResponse> buscar(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                moduloService.buscarModuloPorId(id));
    }

    /**
     * Updates a module.
     *
     * @param id module identifier
     * @param request updated module data
     * @return updated module
     */
    @PutMapping("/{id}")
    public ResponseEntity<ModuloResponse> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody ModuloRequest request) {

        return ResponseEntity.ok(
                moduloService.actualizarModulo(id, request));
    }

    /**
     * Deletes a module.
     *
     * @param id module identifier
     * @return empty response
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {

        moduloService.eliminarModulo(id);

        return ResponseEntity.noContent().build();
    }
}