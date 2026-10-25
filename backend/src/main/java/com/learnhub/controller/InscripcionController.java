package com.learnhub.controller;

import com.learnhub.dto.InscripcionRequest;
import com.learnhub.dto.InscripcionResponse;
import com.learnhub.service.InscripcionService;
import jakarta.validation.Valid;
import com.learnhub.security.UsuarioActual;
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
@RequestMapping("/api/inscripciones")
public class InscripcionController {

    private final InscripcionService inscripcionService;
    private final UsuarioActual usuarioActual;

    public InscripcionController(
            InscripcionService inscripcionService,
            UsuarioActual usuarioActual) {
        this.inscripcionService = inscripcionService;
        this.usuarioActual = usuarioActual;
    }

    @PostMapping
    public ResponseEntity<InscripcionResponse> crearInscripcion(
            @Valid @RequestBody InscripcionRequest request) {

        usuarioActual.exigirPropio(request.getUsuarioId());

        InscripcionResponse response =
                inscripcionService.crearInscripcion(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping
    public ResponseEntity<List<InscripcionResponse>> listarInscripciones() {

        return ResponseEntity.ok(
                soloPropios(inscripcionService.listarInscripciones()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<InscripcionResponse> buscarInscripcionPorId(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                propio(inscripcionService.buscarInscripcionPorId(id)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<InscripcionResponse> actualizarInscripcion(
            @PathVariable Long id,
            @Valid @RequestBody InscripcionRequest request) {

        usuarioActual.exigirPropio(inscripcionService.buscarInscripcionPorId(id).getUsuarioId());
        usuarioActual.exigirPropio(request.getUsuarioId());

        return ResponseEntity.ok(
                inscripcionService.actualizarInscripcion(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarInscripcion(
            @PathVariable Long id) {

        inscripcionService.eliminarInscripcion(id);

        return ResponseEntity.noContent().build();
    }

    private List<InscripcionResponse> soloPropios(List<InscripcionResponse> registros) {
        if (!usuarioActual.esEstudiante()) {
            return registros;
        }

        Long id = usuarioActual.obtener().getId();

        return registros.stream()
                .filter(r -> id.equals(r.getUsuarioId()))
                .toList();
    }

    private InscripcionResponse propio(InscripcionResponse registro) {
        usuarioActual.exigirPropio(registro.getUsuarioId());
        return registro;
    }
}
