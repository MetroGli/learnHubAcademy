package com.learnhub.controller;

import com.learnhub.dto.RespuestaRequest;
import com.learnhub.dto.RespuestaResponse;
import com.learnhub.service.RespuestaService;
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
@RequestMapping("/api/respuestas")
public class RespuestaController {

    private final RespuestaService respuestaService;

    public RespuestaController(RespuestaService respuestaService) {
        this.respuestaService = respuestaService;
    }

    @PostMapping
    public ResponseEntity<RespuestaResponse> crear(
            @Valid @RequestBody RespuestaRequest request) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(respuestaService.crearRespuesta(request));
    }

    @GetMapping
    public ResponseEntity<List<RespuestaResponse>> listar() {
        return ResponseEntity.ok(respuestaService.listarRespuestas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RespuestaResponse> buscar(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                respuestaService.buscarRespuestaPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<RespuestaResponse> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody RespuestaRequest request) {

        return ResponseEntity.ok(
                respuestaService.actualizarRespuesta(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {

        respuestaService.eliminarRespuesta(id);

        return ResponseEntity.noContent().build();
    }
}