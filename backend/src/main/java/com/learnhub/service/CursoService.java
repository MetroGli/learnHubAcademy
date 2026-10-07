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

/**
 * Provides business logic for Curso management.
 */
@Service
public class CursoService {

    private final CursoRepository cursoRepository;
    private final UsuarioRepository usuarioRepository;

    /**
     * Creates the course service with its required repositories.
     *
     * @param cursoRepository repository used to manage courses
     * @param usuarioRepository repository used to manage users
     */
    public CursoService(
            CursoRepository cursoRepository,
            UsuarioRepository usuarioRepository) {
        this.cursoRepository = cursoRepository;
        this.usuarioRepository = usuarioRepository;
    }

    /**
     * Creates a new course.
     *
     * @param request course creation data
     * @return created course
     */
    public CursoResponse crearCurso(CursoRequest request) {
        Usuario profesor = usuarioRepository.findById(request.getProfesorId())
                .orElseThrow(() -> new IllegalArgumentException(
                        "Professor not found with id: " + request.getProfesorId()));

        Curso curso = new Curso();
        curso.setNombre(request.getNombre());
        curso.setDescripcion(request.getDescripcion());
        curso.setActivo(request.isActivo());
        curso.setProfesor(profesor);

        return convertirAResponse(cursoRepository.save(curso));
    }

    /**
     * Retrieves all courses.
     *
     * @return list of courses
     */
    public List<CursoResponse> listarCursos() {
        return cursoRepository.findAll()
                .stream()
                .map(this::convertirAResponse)
                .collect(Collectors.toList());
    }

    /**
     * Retrieves a course by its identifier.
     *
     * @param id course identifier
     * @return course response
     */
    public CursoResponse buscarCursoPorId(Long id) {
        Curso curso = cursoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Course not found with id: " + id));

        return convertirAResponse(curso);
    }

    /**
     * Updates an existing course.
     *
     * @param id course identifier
     * @param request updated course data
     * @return updated course
     */
    public CursoResponse actualizarCurso(Long id, CursoRequest request) {
        Curso curso = cursoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Course not found with id: " + id));

        Usuario profesor = usuarioRepository.findById(request.getProfesorId())
                .orElseThrow(() -> new IllegalArgumentException(
                        "Professor not found with id: " + request.getProfesorId()));

        curso.setNombre(request.getNombre());
        curso.setDescripcion(request.getDescripcion());
        curso.setActivo(request.isActivo());
        curso.setProfesor(profesor);

        return convertirAResponse(cursoRepository.save(curso));
    }

    /**
     * Deletes an existing course.
     *
     * @param id course identifier
     */
    public void eliminarCurso(Long id) {
        if (!cursoRepository.existsById(id)) {
            throw new IllegalArgumentException(
                    "Course not found with id: " + id);
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
        }

        return response;
    }
}
