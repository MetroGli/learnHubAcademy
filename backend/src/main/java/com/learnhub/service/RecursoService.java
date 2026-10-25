package com.learnhub.service;

import com.learnhub.dto.RecursoRequest;
import com.learnhub.dto.RecursoResponse;
import com.learnhub.entity.Leccion;
import com.learnhub.entity.Recurso;
import com.learnhub.repository.LeccionRepository;
import com.learnhub.repository.RecursoRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class RecursoService {

    private final RecursoRepository recursoRepository;
    private final LeccionRepository leccionRepository;

    public RecursoService(
            RecursoRepository recursoRepository,
            LeccionRepository leccionRepository) {
        this.recursoRepository = recursoRepository;
        this.leccionRepository = leccionRepository;
    }

    public RecursoResponse crearRecurso(RecursoRequest request) {
        Leccion leccion = leccionRepository.findById(request.getLeccionId())
                .orElseThrow(() -> new IllegalArgumentException(
                        "No se encontró la lección con id: " + request.getLeccionId()));

        Recurso recurso = new Recurso();
        recurso.setNombre(request.getNombre());
        recurso.setTipo(request.getTipo());
        recurso.setUrl(request.getUrl());
        recurso.setLeccion(leccion);

        return convertirAResponse(recursoRepository.save(recurso));
    }

    public List<RecursoResponse> listarRecursos() {
        return recursoRepository.findAll()
                .stream()
                .map(this::convertirAResponse)
                .collect(Collectors.toList());
    }

    public RecursoResponse buscarRecursoPorId(Long id) {
        Recurso recurso = recursoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                        "No se encontró el recurso con id: " + id));

        return convertirAResponse(recurso);
    }

    public RecursoResponse actualizarRecurso(
            Long id,
            RecursoRequest request) {

        Recurso recurso = recursoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                        "No se encontró el recurso con id: " + id));

        Leccion leccion = leccionRepository.findById(request.getLeccionId())
                .orElseThrow(() -> new IllegalArgumentException(
                        "No se encontró la lección con id: " + request.getLeccionId()));

        recurso.setNombre(request.getNombre());
        recurso.setTipo(request.getTipo());
        recurso.setUrl(request.getUrl());
        recurso.setLeccion(leccion);

        return convertirAResponse(recursoRepository.save(recurso));
    }

    public void eliminarRecurso(Long id) {
        if (!recursoRepository.existsById(id)) {
            throw new IllegalArgumentException(
                    "No se encontró el recurso con id: " + id);
        }

        recursoRepository.deleteById(id);
    }

    private RecursoResponse convertirAResponse(Recurso recurso) {
        RecursoResponse response = new RecursoResponse();
        response.setId(recurso.getId());
        response.setNombre(recurso.getNombre());
        response.setTipo(recurso.getTipo());
        response.setUrl(recurso.getUrl());

        if (recurso.getLeccion() != null) {
            response.setLeccionId(recurso.getLeccion().getId());
        }

        return response;
    }
}
