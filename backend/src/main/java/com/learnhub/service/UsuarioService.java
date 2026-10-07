package com.learnhub.service;

import com.learnhub.entity.Usuario;
import com.learnhub.repository.UsuarioRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Provides business logic for user management.
 */
@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * Creates the user service with its required dependencies.
     *
     * @param usuarioRepository repository used to manage users
     * @param passwordEncoder encoder used to encrypt user passwords
     */
    public UsuarioService(
            UsuarioRepository usuarioRepository,
            PasswordEncoder passwordEncoder) {

        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Creates and persists a new user.
     *
     * @param usuario user to create
     * @return the persisted user
     */
    public Usuario crearUsuario(Usuario usuario) {

        usuario.setPassword(
                passwordEncoder.encode(usuario.getPassword())
        );

        return usuarioRepository.save(usuario);
    }

    /**
     * Retrieves all registered users.
     *
     * @return list containing all users
     */
    public List<Usuario> listarUsuarios() {
        return usuarioRepository.findAll();
    }

    /**
     * Searches for a user by its identifier.
     *
     * @param id user identifier
     * @return optional containing the user if found
     */
    public Optional<Usuario> buscarUsuarioPorId(Long id) {
        return usuarioRepository.findById(id);
    }

    /**
     * Updates an existing user.
     *
     * @param id identifier of the user to update
     * @param usuario updated user information
     * @return the updated user
     * @throws IllegalArgumentException if the user does not exist
     */
    public Usuario actualizarUsuario(Long id, Usuario usuario) {

        Usuario usuarioExistente = usuarioRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                        "User not found with id: " + id));

        usuarioExistente.setNombre(usuario.getNombre());
        usuarioExistente.setApellido(usuario.getApellido());
        usuarioExistente.setEmail(usuario.getEmail());

        usuarioExistente.setPassword(
                passwordEncoder.encode(usuario.getPassword())
        );

        usuarioExistente.setRol(usuario.getRol());

        return usuarioRepository.save(usuarioExistente);
    }

    /**
     * Deletes an existing user.
     *
     * @param id identifier of the user to delete
     * @throws IllegalArgumentException if the user does not exist
     */
    public void eliminarUsuario(Long id) {

        if (!usuarioRepository.existsById(id)) {
            throw new IllegalArgumentException(
                    "User not found with id: " + id);
        }

        usuarioRepository.deleteById(id);
    }
}
