package com.learnhub.service;

import com.learnhub.dto.RespuestaRequest;
import com.learnhub.dto.RespuestaResponse;
import com.learnhub.entity.Pregunta;
import com.learnhub.entity.Respuesta;
import com.learnhub.repository.PreguntaRepository;
import com.learnhub.repository.RespuestaRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

/**
 * Provides business logic for Respuesta management.
 */
@Service
public class RespuestaService {

    private final RespuestaRepository respuestaRepository;
    private final PreguntaRepository preguntaRepository;

    public RespuestaService(
            RespuestaRepository respuestaRepository,
            PreguntaRepository preguntaRepository) {
        this.respuestaRepository = respuestaRepository;
        this.preguntaRepository = preguntaRepository;
    }

    public RespuestaResponse crearRespuesta(RespuestaRequest request) {
        Pregunta pregunta = preguntaRepository.findById(
                        request.getPreguntaId())
                .orElseThrow(() -> new IllegalArgumentException(
                        "Question not found with id: "
                                + request.getPreguntaId()));

        Respuesta respuesta = new Respuesta();
        respuesta.setTexto(request.getTexto());
        respuesta.setCorrecta(request.isCorrecta());
        respuesta.setPregunta(pregunta);

        return convertirAResponse(respuestaRepository.save(respuesta));
    }

    public List<RespuestaResponse> listarRespuestas() {
        return respuestaRepository.findAll()
                .stream()
                .map(this::convertirAResponse)
                .collect(Collectors.toList());
    }

    public RespuestaResponse buscarRespuestaPorId(Long id) {
        Respuesta respuesta = respuestaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Answer not found with id: " + id));

        return convertirAResponse(respuesta);
    }

    public RespuestaResponse actualizarRespuesta(
            Long id,
            RespuestaRequest request) {

        Respuesta respuesta = respuestaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Answer not found with id: " + id));

        Pregunta pregunta = preguntaRepository.findById(
                        request.getPreguntaId())
                .orElseThrow(() -> new IllegalArgumentException(
                        "Question not found with id: "
                                + request.getPreguntaId()));

        respuesta.setTexto(request.getTexto());
        respuesta.setCorrecta(request.isCorrecta());
        respuesta.setPregunta(pregunta);

        return convertirAResponse(respuestaRepository.save(respuesta));
    }

    public void eliminarRespuesta(Long id) {
        if (!respuestaRepository.existsById(id)) {
            throw new IllegalArgumentException(
                    "Answer not found with id: " + id);
        }

        respuestaRepository.deleteById(id);
    }

    private RespuestaResponse convertirAResponse(Respuesta respuesta) {
        RespuestaResponse response = new RespuestaResponse();
        response.setId(respuesta.getId());
        response.setTexto(respuesta.getTexto());
        response.setCorrecta(respuesta.isCorrecta());

        if (respuesta.getPregunta() != null) {
            response.setPreguntaId(respuesta.getPregunta().getId());
        }

        return response;
    }
}
