package com.learnhub.service;

import com.learnhub.dto.ModuloRequest;
import com.learnhub.dto.ModuloResponse;
import com.learnhub.entity.Curso;
import com.learnhub.entity.Modulo;
import com.learnhub.repository.CursoRepository;
import com.learnhub.repository.ModuloRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

/**
 * Provides business logic for Modulo management.
 */
@Service
public class ModuloService {

    private final ModuloRepository moduloRepository;
    private final CursoRepository cursoRepository;

    public ModuloService(
            ModuloRepository moduloRepository,
            CursoRepository cursoRepository) {
        this.moduloRepository = moduloRepository;
        this.cursoRepository = cursoRepository;
    }

    /**
     * Creates a new module.
     *
     * @param request module creation data
     * @return created module
     */
    public ModuloResponse crearModulo(ModuloRequest request) {
        Curso curso = cursoRepository.findById(request.getCursoId())
                .orElseThrow(() -> new IllegalArgumentException(
                        "Course not found with id: " + request.getCursoId()));

        Modulo modulo = new Modulo();
        modulo.setNombre(request.getNombre());
        modulo.setDescripcion(request.getDescripcion());
        modulo.setOrden(request.getOrden());
        modulo.setCurso(curso);

        return convertirAResponse(moduloRepository.save(modulo));
    }

    public List<ModuloResponse> listarModulos() {
        return moduloRepository.findAll()
                .stream()
                .map(this::convertirAResponse)
                .collect(Collectors.toList());
    }

    public ModuloResponse buscarModuloPorId(Long id) {
        Modulo modulo = moduloRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Module not found with id: " + id));

        return convertirAResponse(modulo);
    }

    public ModuloResponse actualizarModulo(Long id, ModuloRequest request) {
        Modulo modulo = moduloRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Module not found with id: " + id));

        Curso curso = cursoRepository.findById(request.getCursoId())
                .orElseThrow(() -> new IllegalArgumentException(
                        "Course not found with id: " + request.getCursoId()));

        modulo.setNombre(request.getNombre());
        modulo.setDescripcion(request.getDescripcion());
        modulo.setOrden(request.getOrden());
        modulo.setCurso(curso);

        return convertirAResponse(moduloRepository.save(modulo));
    }

    public void eliminarModulo(Long id) {
        if (!moduloRepository.existsById(id)) {
            throw new IllegalArgumentException(
                    "Module not found with id: " + id);
        }

        moduloRepository.deleteById(id);
    }

    private ModuloResponse convertirAResponse(Modulo modulo) {
        ModuloResponse response = new ModuloResponse();
        response.setId(modulo.getId());
        response.setNombre(modulo.getNombre());
        response.setDescripcion(modulo.getDescripcion());
        response.setOrden(modulo.getOrden());

        if (modulo.getCurso() != null) {
            response.setCursoId(modulo.getCurso().getId());
        }

        return response;
    }
}
