package com.learnhub.security;

import com.learnhub.entity.Rol;
import com.learnhub.entity.Usuario;
import com.learnhub.repository.UsuarioRepository;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

/**
 * Da acceso al usuario que hace la petición y valida que un estudiante
 * solo toque registros propios (inscripciones, progreso, calificaciones).
 */
@Component
public class UsuarioActual {

    private final UsuarioRepository usuarioRepository;

    public UsuarioActual(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public Usuario obtener() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null) {
            throw new AccessDeniedException("No hay una sesión activa.");
        }

        return usuarioRepository.findByEmail(auth.getName())
                .orElseThrow(() -> new AccessDeniedException("No hay una sesión activa."));
    }

    public boolean esEstudiante() {
        return obtener().getRol() == Rol.ESTUDIANTE;
    }

    /**
     * Profesores y administradores pueden gestionar registros de cualquiera;
     * un estudiante, solo los suyos.
     */
    public void exigirPropio(Long usuarioId) {
        Usuario usuario = obtener();

        if (usuario.getRol() == Rol.ESTUDIANTE && !usuario.getId().equals(usuarioId)) {
            throw new AccessDeniedException(
                    "No tienes permiso para modificar datos de otro usuario.");
        }
    }
}
