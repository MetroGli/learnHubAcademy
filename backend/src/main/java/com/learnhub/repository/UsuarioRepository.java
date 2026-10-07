package com.learnhub.repository;

import com.learnhub.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Provides database operations for users.
 */
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    /**
     * Finds a user by email address.
     *
     * @param email the email address
     * @return the user if found
     */
    Optional<Usuario> findByEmail(String email);
}