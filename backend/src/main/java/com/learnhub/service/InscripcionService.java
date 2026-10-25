package com.learnhub.service;

import com.learnhub.dto.InscripcionRequest;
import com.learnhub.dto.InscripcionResponse;
import com.learnhub.entity.Curso;
import com.learnhub.entity.Inscripcion;
import com.learnhub.entity.Usuario;
import com.learnhub.repository.CursoRepository;
import com.learnhub.repository.InscripcionRepository;
import com.learnhub.repository.UsuarioRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class InscripcionService {

    private final InscripcionRepository inscripcionRepository;
    private final UsuarioRepository usuarioRepository;
    private final CursoRepository cursoRepository;

    public InscripcionService(
            InscripcionRepository inscripcionRepository,
            UsuarioRepository usuarioRepository,
            CursoRepository cursoRepository) {
        this.inscripcionRepository = inscripcionRepository;
        this.usuarioRepository = usuarioRepository;
        this.cursoRepository = cursoRepository;
    }

    public InscripcionResponse crearInscripcion(
            InscripcionRequest request) {

        Usuario usuario = usuarioRepository.findById(
                        request.getUsuarioId())
                .orElseThrow(() -> new IllegalArgumentException(
                        "No se encontró el usuario con id: "
                                + request.getUsuarioId()));

        Curso curso = cursoRepository.findById(
                        request.getCursoId())
                .orElseThrow(() -> new IllegalArgumentException(
                        "No se encontró el curso con id: "
                                + request.getCursoId()));

        Inscripcion inscripcion = new Inscripcion();
        inscripcion.setUsuario(usuario);
        inscripcion.setCurso(curso);
        inscripcion.setFechaInscripcion(LocalDate.now());
        inscripcion.setActiva(request.isActiva());

        return convertirAResponse(
                inscripcionRepository.save(inscripcion));
    }

    public List<InscripcionResponse> listarInscripciones() {
        return inscripcionRepository.findAll()
                .stream()
                .map(this::convertirAResponse)
                .collect(Collectors.toList());
    }

    public InscripcionResponse buscarInscripcionPorId(Long id) {
        Inscripcion inscripcion = inscripcionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                        "No se encontró la inscripción con id: " + id));

        return convertirAResponse(inscripcion);
    }

    public InscripcionResponse actualizarInscripcion(
            Long id,
            InscripcionRequest request) {

        Inscripcion inscripcion = inscripcionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                        "No se encontró la inscripción con id: " + id));

        Usuario usuario = usuarioRepository.findById(
                        request.getUsuarioId())
                .orElseThrow(() -> new IllegalArgumentException(
                        "No se encontró el usuario con id: "
                                + request.getUsuarioId()));

        Curso curso = cursoRepository.findById(
                        request.getCursoId())
                .orElseThrow(() -> new IllegalArgumentException(
                        "No se encontró el curso con id: "
                                + request.getCursoId()));

        inscripcion.setUsuario(usuario);
        inscripcion.setCurso(curso);
        inscripcion.setActiva(request.isActiva());

        return convertirAResponse(
                inscripcionRepository.save(inscripcion));
    }

    public void eliminarInscripcion(Long id) {
        if (!inscripcionRepository.existsById(id)) {
            throw new IllegalArgumentException(
                    "No se encontró la inscripción con id: " + id);
        }

        inscripcionRepository.deleteById(id);
    }

    private InscripcionResponse convertirAResponse(
            Inscripcion inscripcion) {

        InscripcionResponse response = new InscripcionResponse();
        response.setId(inscripcion.getId());
        response.setFechaInscripcion(
                inscripcion.getFechaInscripcion());
        response.setActiva(inscripcion.isActiva());

        if (inscripcion.getUsuario() != null) {
            response.setUsuarioId(
                    inscripcion.getUsuario().getId());
        }

        if (inscripcion.getCurso() != null) {
            response.setCursoId(
                    inscripcion.getCurso().getId());
        }

        return response;
    }
}