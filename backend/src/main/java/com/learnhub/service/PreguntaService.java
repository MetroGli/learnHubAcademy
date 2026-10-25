package com.learnhub.service;

import com.learnhub.dto.PreguntaRequest;
import com.learnhub.dto.PreguntaResponse;
import com.learnhub.entity.Evaluacion;
import com.learnhub.entity.Pregunta;
import com.learnhub.repository.EvaluacionRepository;
import com.learnhub.repository.PreguntaRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class PreguntaService {

    private final PreguntaRepository preguntaRepository;
    private final EvaluacionRepository evaluacionRepository;

    public PreguntaService(
            PreguntaRepository preguntaRepository,
            EvaluacionRepository evaluacionRepository) {
        this.preguntaRepository = preguntaRepository;
        this.evaluacionRepository = evaluacionRepository;
    }

    public PreguntaResponse crearPregunta(PreguntaRequest request) {
        Evaluacion evaluacion = evaluacionRepository.findById(
                        request.getEvaluacionId())
                .orElseThrow(() -> new IllegalArgumentException(
                        "No se encontró la evaluación con id: "
                                + request.getEvaluacionId()));

        Pregunta pregunta = new Pregunta();
        pregunta.setEnunciado(request.getEnunciado());
        pregunta.setTipo(request.getTipo());
        pregunta.setEvaluacion(evaluacion);

        return convertirAResponse(preguntaRepository.save(pregunta));
    }

    public List<PreguntaResponse> listarPreguntas() {
        return preguntaRepository.findAll()
                .stream()
                .map(this::convertirAResponse)
                .collect(Collectors.toList());
    }

    public PreguntaResponse buscarPreguntaPorId(Long id) {
        Pregunta pregunta = preguntaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                        "No se encontró la pregunta con id: " + id));

        return convertirAResponse(pregunta);
    }

    public PreguntaResponse actualizarPregunta(
            Long id,
            PreguntaRequest request) {

        Pregunta pregunta = preguntaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                        "No se encontró la pregunta con id: " + id));

        Evaluacion evaluacion = evaluacionRepository.findById(
                        request.getEvaluacionId())
                .orElseThrow(() -> new IllegalArgumentException(
                        "No se encontró la evaluación con id: "
                                + request.getEvaluacionId()));

        pregunta.setEnunciado(request.getEnunciado());
        pregunta.setTipo(request.getTipo());
        pregunta.setEvaluacion(evaluacion);

        return convertirAResponse(preguntaRepository.save(pregunta));
    }

    public void eliminarPregunta(Long id) {
        if (!preguntaRepository.existsById(id)) {
            throw new IllegalArgumentException(
                    "No se encontró la pregunta con id: " + id);
        }

        preguntaRepository.deleteById(id);
    }

    private PreguntaResponse convertirAResponse(Pregunta pregunta) {
        PreguntaResponse response = new PreguntaResponse();
        response.setId(pregunta.getId());
        response.setEnunciado(pregunta.getEnunciado());
        response.setTipo(pregunta.getTipo());

        if (pregunta.getEvaluacion() != null) {
            response.setEvaluacionId(
                    pregunta.getEvaluacion().getId());
        }

        return response;
    }
}
