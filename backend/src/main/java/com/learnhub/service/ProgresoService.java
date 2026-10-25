package com.learnhub.service;

import com.learnhub.dto.ProgresoRequest;
import com.learnhub.dto.ProgresoResponse;
import com.learnhub.entity.Curso;
import com.learnhub.entity.Progreso;
import com.learnhub.entity.Usuario;
import com.learnhub.repository.CursoRepository;
import com.learnhub.repository.ProgresoRepository;
import com.learnhub.repository.UsuarioRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class ProgresoService {

    private final ProgresoRepository progresoRepository;
    private final UsuarioRepository usuarioRepository;
    private final CursoRepository cursoRepository;

    public ProgresoService(
            ProgresoRepository progresoRepository,
            UsuarioRepository usuarioRepository,
            CursoRepository cursoRepository) {
        this.progresoRepository = progresoRepository;
        this.usuarioRepository = usuarioRepository;
        this.cursoRepository = cursoRepository;
    }

    public ProgresoResponse crearProgreso(ProgresoRequest request) {
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

        Progreso progreso = new Progreso();
        progreso.setUsuario(usuario);
        progreso.setCurso(curso);
        progreso.setPorcentaje(request.getPorcentaje());

        return convertirAResponse(
                progresoRepository.save(progreso));
    }

    public List<ProgresoResponse> listarProgresos() {
        return progresoRepository.findAll()
                .stream()
                .map(this::convertirAResponse)
                .collect(Collectors.toList());
    }

    public ProgresoResponse buscarProgresoPorId(Long id) {
        Progreso progreso = progresoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                        "No se encontró el progreso con id: " + id));

        return convertirAResponse(progreso);
    }

    public ProgresoResponse actualizarProgreso(
            Long id,
            ProgresoRequest request) {

        Progreso progreso = progresoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                        "No se encontró el progreso con id: " + id));

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

        progreso.setUsuario(usuario);
        progreso.setCurso(curso);
        progreso.setPorcentaje(request.getPorcentaje());

        return convertirAResponse(
                progresoRepository.save(progreso));
    }

    public void eliminarProgreso(Long id) {
        if (!progresoRepository.existsById(id)) {
            throw new IllegalArgumentException(
                    "No se encontró el progreso con id: " + id);
        }

        progresoRepository.deleteById(id);
    }

    private ProgresoResponse convertirAResponse(Progreso progreso) {
        ProgresoResponse response = new ProgresoResponse();
        response.setId(progreso.getId());
        response.setPorcentaje(progreso.getPorcentaje());

        if (progreso.getUsuario() != null) {
            response.setUsuarioId(
                    progreso.getUsuario().getId());
        }

        if (progreso.getCurso() != null) {
            response.setCursoId(
                    progreso.getCurso().getId());
        }

        return response;
    }
}
