package com.learnhub.controller;

import com.learnhub.dto.UsuarioRequest;
import com.learnhub.dto.UsuarioResponse;
import com.learnhub.entity.Usuario;
import com.learnhub.service.UsuarioService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Provides REST endpoints for user management.
 */
@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    /**
     * Creates the user controller with its required service.
     *
     * @param usuarioService service used to manage users
     */
    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    /**
     * Creates a new user.
     *
     * @param request user creation data
     * @return created user data
     */
    @PostMapping
    public ResponseEntity<UsuarioResponse> crearUsuario(
            @Valid @RequestBody UsuarioRequest request) {

        Usuario usuario = new Usuario();
        usuario.setNombre(request.getNombre());
        usuario.setApellido(request.getApellido());
        usuario.setEmail(request.getEmail());
        usuario.setPassword(request.getPassword());
        usuario.setRol(request.getRol());
        usuario.setActivo(true);

        Usuario usuarioGuardado = usuarioService.crearUsuario(usuario);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(convertirAResponse(usuarioGuardado));
    }

    /**
     * Retrieves all registered users.
     *
     * @return list of user data
     */
    @GetMapping
    public ResponseEntity<List<UsuarioResponse>> listarUsuarios() {

        List<UsuarioResponse> usuarios = usuarioService.listarUsuarios()
                .stream()
                .map(this::convertirAResponse)
                .toList();

        return ResponseEntity.ok(usuarios);
    }

    /**
     * Retrieves a user by its identifier.
     *
     * @param id user identifier
     * @return user data
     */
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponse> buscarUsuarioPorId(
            @PathVariable Long id) {

        return usuarioService.buscarUsuarioPorId(id)
                .map(this::convertirAResponse)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Updates an existing user.
     *
     * @param id user identifier
     * @param request updated user data
     * @return updated user data
     */
    @PutMapping("/{id}")
    public ResponseEntity<UsuarioResponse> actualizarUsuario(
            @PathVariable Long id,
            @Valid @RequestBody UsuarioRequest request) {

        Usuario usuario = new Usuario();
        usuario.setNombre(request.getNombre());
        usuario.setApellido(request.getApellido());
        usuario.setEmail(request.getEmail());
        usuario.setPassword(request.getPassword());
        usuario.setRol(request.getRol());

        Usuario usuarioActualizado =
                usuarioService.actualizarUsuario(id, usuario);

        return ResponseEntity.ok(convertirAResponse(usuarioActualizado));
    }

    /**
     * Deletes an existing user.
     *
     * @param id user identifier
     * @return empty response
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarUsuario(@PathVariable Long id) {

        usuarioService.eliminarUsuario(id);

        return ResponseEntity.noContent().build();
    }

    /**
     * Converts a user entity to a response DTO.
     *
     * @param usuario user entity
     * @return user response data
     */
    private UsuarioResponse convertirAResponse(Usuario usuario) {

        UsuarioResponse response = new UsuarioResponse();

        response.setId(usuario.getId());
        response.setNombre(usuario.getNombre());
        response.setApellido(usuario.getApellido());
        response.setEmail(usuario.getEmail());
        response.setRol(usuario.getRol());
        response.setActivo(usuario.isActivo());

        return response;
    }
}