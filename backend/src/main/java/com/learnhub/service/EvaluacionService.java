package com.learnhub.service;

import com.learnhub.dto.EvaluacionRequest;
import com.learnhub.dto.EvaluacionResponse;
import com.learnhub.entity.Curso;
import com.learnhub.entity.Evaluacion;
import com.learnhub.repository.CursoRepository;
import com.learnhub.repository.EvaluacionRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class EvaluacionService {

    private final EvaluacionRepository evaluacionRepository;
    private final CursoRepository cursoRepository;

    public EvaluacionService(
            EvaluacionRepository evaluacionRepository,
            CursoRepository cursoRepository) {
        this.evaluacionRepository = evaluacionRepository;
        this.cursoRepository = cursoRepository;
    }

    public EvaluacionResponse crearEvaluacion(EvaluacionRequest request) {
        Curso curso = cursoRepository.findById(request.getCursoId())
                .orElseThrow(() -> new IllegalArgumentException(
                        "No se encontró el curso con id: " + request.getCursoId()));

        Evaluacion evaluacion = new Evaluacion();
        evaluacion.setTitulo(request.getTitulo());
        evaluacion.setDescripcion(request.getDescripcion());
        evaluacion.setPorcentaje(request.getPorcentaje());
        evaluacion.setCurso(curso);

        return convertirAResponse(evaluacionRepository.save(evaluacion));
    }

    public List<EvaluacionResponse> listarEvaluaciones() {
        return evaluacionRepository.findAll()
                .stream()
                .map(this::convertirAResponse)
                .collect(Collectors.toList());
    }

    public EvaluacionResponse buscarEvaluacionPorId(Long id) {
        Evaluacion evaluacion = evaluacionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                        "No se encontró la evaluación con id: " + id));

        return convertirAResponse(evaluacion);
    }

    public EvaluacionResponse actualizarEvaluacion(
            Long id,
            EvaluacionRequest request) {

        Evaluacion evaluacion = evaluacionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                        "No se encontró la evaluación con id: " + id));

        Curso curso = cursoRepository.findById(request.getCursoId())
                .orElseThrow(() -> new IllegalArgumentException(
                        "No se encontró el curso con id: " + request.getCursoId()));

        evaluacion.setTitulo(request.getTitulo());
        evaluacion.setDescripcion(request.getDescripcion());
        evaluacion.setPorcentaje(request.getPorcentaje());
        evaluacion.setCurso(curso);

        return convertirAResponse(evaluacionRepository.save(evaluacion));
    }

    public void eliminarEvaluacion(Long id) {
        if (!evaluacionRepository.existsById(id)) {
            throw new IllegalArgumentException(
                    "No se encontró la evaluación con id: " + id);
        }

        evaluacionRepository.deleteById(id);
    }

    private EvaluacionResponse convertirAResponse(Evaluacion evaluacion) {
        EvaluacionResponse response = new EvaluacionResponse();
        response.setId(evaluacion.getId());
        response.setTitulo(evaluacion.getTitulo());
        response.setDescripcion(evaluacion.getDescripcion());
        response.setPorcentaje(evaluacion.getPorcentaje());

        if (evaluacion.getCurso() != null) {
            response.setCursoId(evaluacion.getCurso().getId());
        }

        return response;
    }
}
