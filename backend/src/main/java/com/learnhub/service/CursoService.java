package com.learnhub.service;

import com.learnhub.dto.CursoRequest;
import com.learnhub.dto.CursoResponse;
import com.learnhub.entity.Curso;
import com.learnhub.entity.Usuario;
import com.learnhub.repository.CursoRepository;
import com.learnhub.repository.UsuarioRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class CursoService {

    private final CursoRepository cursoRepository;
    private final UsuarioRepository usuarioRepository;

    public CursoService(
            CursoRepository cursoRepository,
            UsuarioRepository usuarioRepository) {
        this.cursoRepository = cursoRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public CursoResponse crearCurso(CursoRequest request) {
        Usuario profesor = usuarioRepository.findById(request.getProfesorId())
                .orElseThrow(() -> new IllegalArgumentException(
                        "No se encontró el profesor con id: " + request.getProfesorId()));

        Curso curso = new Curso();
        curso.setNombre(request.getNombre());
        curso.setDescripcion(request.getDescripcion());
        curso.setActivo(request.isActivo());
        curso.setProfesor(profesor);
        curso.setCategoria(limpiar(request.getCategoria()));
        curso.setNivel(limpiar(request.getNivel()));
        curso.setDuracionHoras(request.getDuracionHoras());

        return convertirAResponse(cursoRepository.save(curso));
    }

    public List<CursoResponse> listarCursos() {
        return cursoRepository.findAll()
                .stream()
                .map(this::convertirAResponse)
                .collect(Collectors.toList());
    }

    public CursoResponse buscarCursoPorId(Long id) {
        Curso curso = cursoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                        "No se encontró el curso con id: " + id));

        return convertirAResponse(curso);
    }

    public CursoResponse actualizarCurso(Long id, CursoRequest request) {
        Curso curso = cursoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                        "No se encontró el curso con id: " + id));

        Usuario profesor = usuarioRepository.findById(request.getProfesorId())
                .orElseThrow(() -> new IllegalArgumentException(
                        "No se encontró el profesor con id: " + request.getProfesorId()));

        curso.setNombre(request.getNombre());
        curso.setDescripcion(request.getDescripcion());
        curso.setActivo(request.isActivo());
        curso.setProfesor(profesor);
        curso.setCategoria(limpiar(request.getCategoria()));
        curso.setNivel(limpiar(request.getNivel()));
        curso.setDuracionHoras(request.getDuracionHoras());

        return convertirAResponse(cursoRepository.save(curso));
    }

    public void eliminarCurso(Long id) {
        if (!cursoRepository.existsById(id)) {
            throw new IllegalArgumentException(
                    "No se encontró el curso con id: " + id);
        }

        cursoRepository.deleteById(id);
    }

    private CursoResponse convertirAResponse(Curso curso) {
        CursoResponse response = new CursoResponse();
        response.setId(curso.getId());
        response.setNombre(curso.getNombre());
        response.setDescripcion(curso.getDescripcion());
        response.setActivo(curso.isActivo());

        if (curso.getProfesor() != null) {
            response.setProfesorId(curso.getProfesor().getId());
            response.setProfesorNombre(curso.getProfesor().getNombre()
                    + " " + curso.getProfesor().getApellido());
        }

        response.setCategoria(curso.getCategoria());
        response.setNivel(curso.getNivel());
        response.setDuracionHoras(curso.getDuracionHoras());

        return response;
    }

    private String limpiar(String texto) {
        return texto == null || texto.isBlank() ? null : texto.trim();
    }
}
