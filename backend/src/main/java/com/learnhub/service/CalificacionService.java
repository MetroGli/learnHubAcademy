package com.learnhub.service;

import com.learnhub.dto.CalificacionRequest;
import com.learnhub.dto.CalificacionResponse;
import com.learnhub.entity.Calificacion;
import com.learnhub.entity.Evaluacion;
import com.learnhub.entity.Usuario;
import com.learnhub.repository.CalificacionRepository;
import com.learnhub.repository.EvaluacionRepository;
import com.learnhub.repository.UsuarioRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

/**
 * Provides business logic for Calificacion management.
 */
@Service
public class CalificacionService {

    private final CalificacionRepository calificacionRepository;
    private final UsuarioRepository usuarioRepository;
    private final EvaluacionRepository evaluacionRepository;

    public CalificacionService(
            CalificacionRepository calificacionRepository,
            UsuarioRepository usuarioRepository,
            EvaluacionRepository evaluacionRepository) {
        this.calificacionRepository = calificacionRepository;
        this.usuarioRepository = usuarioRepository;
        this.evaluacionRepository = evaluacionRepository;
    }

    public CalificacionResponse crearCalificacion(
            CalificacionRequest request) {

        Usuario usuario = usuarioRepository.findById(
                        request.getUsuarioId())
                .orElseThrow(() -> new IllegalArgumentException(
                        "User not found with id: "
                                + request.getUsuarioId()));

        Evaluacion evaluacion = evaluacionRepository.findById(
                        request.getEvaluacionId())
                .orElseThrow(() -> new IllegalArgumentException(
                        "Evaluation not found with id: "
                                + request.getEvaluacionId()));

        Calificacion calificacion = new Calificacion();
        calificacion.setUsuario(usuario);
        calificacion.setEvaluacion(evaluacion);
        calificacion.setNota(request.getNota());

        return convertirAResponse(
                calificacionRepository.save(calificacion));
    }

    public List<CalificacionResponse> listarCalificaciones() {
        return calificacionRepository.findAll()
                .stream()
                .map(this::convertirAResponse)
                .collect(Collectors.toList());
    }

    public CalificacionResponse buscarCalificacionPorId(Long id) {
        Calificacion calificacion = calificacionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Grade not found with id: " + id));

        return convertirAResponse(calificacion);
    }

    public CalificacionResponse actualizarCalificacion(
            Long id,
            CalificacionRequest request) {

        Calificacion calificacion = calificacionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Grade not found with id: " + id));

        Usuario usuario = usuarioRepository.findById(
                        request.getUsuarioId())
                .orElseThrow(() -> new IllegalArgumentException(
                        "User not found with id: "
                                + request.getUsuarioId()));

        Evaluacion evaluacion = evaluacionRepository.findById(
                        request.getEvaluacionId())
                .orElseThrow(() -> new IllegalArgumentException(
                        "Evaluation not found with id: "
                                + request.getEvaluacionId()));

        calificacion.setUsuario(usuario);
        calificacion.setEvaluacion(evaluacion);
        calificacion.setNota(request.getNota());

        return convertirAResponse(
                calificacionRepository.save(calificacion));
    }

    public void eliminarCalificacion(Long id) {
        if (!calificacionRepository.existsById(id)) {
            throw new IllegalArgumentException(
                    "Grade not found with id: " + id);
        }

        calificacionRepository.deleteById(id);
    }

    private CalificacionResponse convertirAResponse(
            Calificacion calificacion) {

        CalificacionResponse response = new CalificacionResponse();
        response.setId(calificacion.getId());
        response.setNota(calificacion.getNota());

        if (calificacion.getUsuario() != null) {
            response.setUsuarioId(
                    calificacion.getUsuario().getId());
        }

        if (calificacion.getEvaluacion() != null) {
            response.setEvaluacionId(
                    calificacion.getEvaluacion().getId());
        }

        return response;
    }
}
