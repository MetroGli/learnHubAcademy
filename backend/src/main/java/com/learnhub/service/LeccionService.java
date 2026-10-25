package com.learnhub.service;

import com.learnhub.dto.LeccionRequest;
import com.learnhub.dto.LeccionResponse;
import com.learnhub.entity.Leccion;
import com.learnhub.entity.Modulo;
import com.learnhub.repository.LeccionRepository;
import com.learnhub.repository.ModuloRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class LeccionService {

    private final LeccionRepository leccionRepository;
    private final ModuloRepository moduloRepository;

    public LeccionService(
            LeccionRepository leccionRepository,
            ModuloRepository moduloRepository) {
        this.leccionRepository = leccionRepository;
        this.moduloRepository = moduloRepository;
    }

    public LeccionResponse crearLeccion(LeccionRequest request) {
        Modulo modulo = moduloRepository.findById(request.getModuloId())
                .orElseThrow(() -> new IllegalArgumentException(
                        "No se encontró el módulo con id: " + request.getModuloId()));

        Leccion leccion = new Leccion();
        leccion.setTitulo(request.getTitulo());
        leccion.setContenido(request.getContenido());
        leccion.setOrden(request.getOrden());
        leccion.setModulo(modulo);

        return convertirAResponse(leccionRepository.save(leccion));
    }

    public List<LeccionResponse> listarLecciones() {
        return leccionRepository.findAll()
                .stream()
                .map(this::convertirAResponse)
                .collect(Collectors.toList());
    }

    public LeccionResponse buscarLeccionPorId(Long id) {
        Leccion leccion = leccionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                        "No se encontró la lección con id: " + id));

        return convertirAResponse(leccion);
    }

    public LeccionResponse actualizarLeccion(Long id, LeccionRequest request) {
        Leccion leccion = leccionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                        "No se encontró la lección con id: " + id));

        Modulo modulo = moduloRepository.findById(request.getModuloId())
                .orElseThrow(() -> new IllegalArgumentException(
                        "No se encontró el módulo con id: " + request.getModuloId()));

        leccion.setTitulo(request.getTitulo());
        leccion.setContenido(request.getContenido());
        leccion.setOrden(request.getOrden());
        leccion.setModulo(modulo);

        return convertirAResponse(leccionRepository.save(leccion));
    }

    public void eliminarLeccion(Long id) {
        if (!leccionRepository.existsById(id)) {
            throw new IllegalArgumentException(
                    "No se encontró la lección con id: " + id);
        }

        leccionRepository.deleteById(id);
    }

    private LeccionResponse convertirAResponse(Leccion leccion) {
        LeccionResponse response = new LeccionResponse();
        response.setId(leccion.getId());
        response.setTitulo(leccion.getTitulo());
        response.setContenido(leccion.getContenido());
        response.setOrden(leccion.getOrden());

        if (leccion.getModulo() != null) {
            response.setModuloId(leccion.getModulo().getId());
        }

        return response;
    }
}
