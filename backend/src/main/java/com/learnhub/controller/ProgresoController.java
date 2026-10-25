package com.learnhub.controller;

import com.learnhub.dto.ProgresoRequest;
import com.learnhub.dto.ProgresoResponse;
import com.learnhub.service.ProgresoService;
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
@RequestMapping("/api/progresos")
public class ProgresoController {

    private final ProgresoService progresoService;
    private final UsuarioActual usuarioActual;

    public ProgresoController(
            ProgresoService progresoService,
            UsuarioActual usuarioActual) {
        this.progresoService = progresoService;
        this.usuarioActual = usuarioActual;
    }

    @PostMapping
    public ResponseEntity<ProgresoResponse> crear(
            @Valid @RequestBody ProgresoRequest request) {

        usuarioActual.exigirPropio(request.getUsuarioId());

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(progresoService.crearProgreso(request));
    }

    @GetMapping
    public ResponseEntity<List<ProgresoResponse>> listar() {
        return ResponseEntity.ok(soloPropios(progresoService.listarProgresos()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProgresoResponse> buscar(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                propio(progresoService.buscarProgresoPorId(id)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProgresoResponse> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody ProgresoRequest request) {

        usuarioActual.exigirPropio(progresoService.buscarProgresoPorId(id).getUsuarioId());
        usuarioActual.exigirPropio(request.getUsuarioId());

        return ResponseEntity.ok(
                progresoService.actualizarProgreso(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {

        progresoService.eliminarProgreso(id);

        return ResponseEntity.noContent().build();
    }

    private List<ProgresoResponse> soloPropios(List<ProgresoResponse> registros) {
        if (!usuarioActual.esEstudiante()) {
            return registros;
        }

        Long id = usuarioActual.obtener().getId();

        return registros.stream()
                .filter(r -> id.equals(r.getUsuarioId()))
                .toList();
    }

    private ProgresoResponse propio(ProgresoResponse registro) {
        usuarioActual.exigirPropio(registro.getUsuarioId());
        return registro;
    }
}
