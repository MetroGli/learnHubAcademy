package com.learnhub.controller;

import com.learnhub.entity.Rol;
import com.learnhub.repository.UsuarioRepository;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Lista de estudiantes para profesores y administradores (por ejemplo, para
 * ver los nombres en la tabla de un curso o inscribir a alguien). A diferencia
 * de /api/usuarios, no expone otros roles ni permite modificar nada.
 */
@RestController
@RequestMapping("/api/estudiantes")
public class EstudianteController {

    private final UsuarioRepository usuarioRepository;

    public EstudianteController(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public record EstudianteResumen(
            Long id, String nombre, String apellido, String email, boolean activo) {
    }

    @GetMapping
    public ResponseEntity<List<EstudianteResumen>> listar() {
        return ResponseEntity.ok(usuarioRepository
                .findByRolOrderByNombreAscApellidoAsc(Rol.ESTUDIANTE)
                .stream()
                .map(u -> new EstudianteResumen(
                        u.getId(), u.getNombre(), u.getApellido(), u.getEmail(), u.isActivo()))
                .toList());
    }
}
