package com.learnhub.repository;

import com.learnhub.entity.Usuario;
import java.util.List;
import com.learnhub.entity.Rol;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByEmail(String email);

    List<Usuario> findByRolOrderByNombreAscApellidoAsc(Rol rol);
}
