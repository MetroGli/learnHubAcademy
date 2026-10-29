package com.learnhub.controller;

import com.learnhub.dto.RespuestaRequest;
import com.learnhub.dto.RespuestaResponse;
import com.learnhub.service.RespuestaService;
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
@RequestMapping("/api/respuestas")
public class RespuestaController {

    private final RespuestaService respuestaService;
    private final UsuarioActual usuarioActual;

    public RespuestaController(
            RespuestaService respuestaService,
            UsuarioActual usuarioActual) {
        this.respuestaService = respuestaService;
        this.usuarioActual = usuarioActual;
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
        List<RespuestaResponse> respuestas = respuestaService.listarRespuestas();

        if (usuarioActual.esEstudiante()) {
            respuestas.forEach(r -> r.setCorrecta(null));
        }

        return ResponseEntity.ok(respuestas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RespuestaResponse> buscar(
            @PathVariable Long id) {

        RespuestaResponse respuesta = respuestaService.buscarRespuestaPorId(id);
        ocultarSiEsEstudiante(respuesta);

        return ResponseEntity.ok(respuesta);
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

    // El estudiante no debe saber cuál es la opción correcta antes de responder:
    // la evaluación se califica en el servidor (/api/mi-avance/evaluaciones).
    private void ocultarSiEsEstudiante(RespuestaResponse respuesta) {
        if (usuarioActual.esEstudiante()) {
            respuesta.setCorrecta(null);
        }
    }
}
