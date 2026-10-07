package com.learnhub.service;

import com.learnhub.entity.Rol;
import com.learnhub.entity.Usuario;
import com.learnhub.repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Tests the business logic for user management.
 */
@ExtendWith(MockitoExtension.class)
class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UsuarioService usuarioService;

    /**
     * Stubs the password encoder to act as a passthrough so tests
     * that don't care about hashing don't have to configure it,
     * and existing assertions on plain-text passwords still pass.
     */
    @BeforeEach
    void setUp() {
        lenient().when(passwordEncoder.encode(anyString()))
                .thenAnswer(invocation -> invocation.getArgument(0));
    }

    /**
     * Tests that a user can be created successfully.
     */
    @Test
    void shouldCreateUser() {
        Usuario usuario = crearUsuario();

        when(usuarioRepository.save(usuario)).thenReturn(usuario);

        Usuario resultado = usuarioService.crearUsuario(usuario);

        assertEquals(usuario, resultado);
        verify(usuarioRepository).save(usuario);
    }

    /**
     * Tests that all users can be retrieved.
     */
    @Test
    void shouldListUsers() {
        Usuario usuario = crearUsuario();

        when(usuarioRepository.findAll()).thenReturn(List.of(usuario));

        List<Usuario> resultado = usuarioService.listarUsuarios();

        assertEquals(1, resultado.size());
        assertEquals(usuario, resultado.get(0));
        verify(usuarioRepository).findAll();
    }

    /**
     * Tests that a user can be found by its identifier.
     */
    @Test
    void shouldFindUserById() {
        Usuario usuario = crearUsuario();
        usuario.setId(1L);

        when(usuarioRepository.findById(1L))
                .thenReturn(Optional.of(usuario));

        Optional<Usuario> resultado = usuarioService.buscarUsuarioPorId(1L);

        assertTrue(resultado.isPresent());
        assertEquals(1L, resultado.get().getId());
        assertEquals("Juan", resultado.get().getNombre());
        assertEquals("Camacho", resultado.get().getApellido());
        verify(usuarioRepository).findById(1L);
    }

    /**
     * Tests that an empty optional is returned when a user does not exist.
     */
    @Test
    void shouldReturnEmptyWhenUserDoesNotExist() {
        when(usuarioRepository.findById(999L))
                .thenReturn(Optional.empty());

        Optional<Usuario> resultado = usuarioService.buscarUsuarioPorId(999L);

        assertTrue(resultado.isEmpty());
        verify(usuarioRepository).findById(999L);
    }

    /**
     * Tests that an existing user can be updated.
     */
    @Test
    void shouldUpdateUser() {
        Usuario usuarioExistente = crearUsuario();
        usuarioExistente.setId(1L);

        Usuario datosActualizados = crearUsuario();
        datosActualizados.setNombre("Juan Pablo");
        datosActualizados.setApellido("Camacho Penata");
        datosActualizados.setEmail("juan.updated@example.com");
        datosActualizados.setPassword("LearnHub456");
        datosActualizados.setRol(Rol.PROFESOR);

        when(usuarioRepository.findById(1L))
                .thenReturn(Optional.of(usuarioExistente));
        when(usuarioRepository.save(usuarioExistente))
                .thenReturn(usuarioExistente);

        Usuario resultado = usuarioService.actualizarUsuario(
                1L,
                datosActualizados
        );

        assertEquals("Juan Pablo", resultado.getNombre());
        assertEquals("Camacho Penata", resultado.getApellido());
        assertEquals("juan.updated@example.com", resultado.getEmail());
        assertEquals("LearnHub456", resultado.getPassword());
        assertEquals(Rol.PROFESOR, resultado.getRol());

        verify(usuarioRepository).findById(1L);
        verify(usuarioRepository).save(usuarioExistente);
    }

    /**
     * Tests that updating a nonexistent user throws an exception.
     */
    @Test
    void shouldThrowExceptionWhenUpdatingNonexistentUser() {
        when(usuarioRepository.findById(999L))
                .thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> usuarioService.actualizarUsuario(
                        999L,
                        crearUsuario()
                )
        );

        assertEquals(
                "User not found with id: 999",
                exception.getMessage()
        );
    }

    /**
     * Tests that an existing user can be deleted.
     */
    @Test
    void shouldDeleteUser() {
        when(usuarioRepository.existsById(1L))
                .thenReturn(true);

        usuarioService.eliminarUsuario(1L);

        verify(usuarioRepository).existsById(1L);
        verify(usuarioRepository).deleteById(1L);
    }

    /**
     * Tests that deleting a nonexistent user throws an exception.
     */
    @Test
    void shouldThrowExceptionWhenDeletingNonexistentUser() {
        when(usuarioRepository.existsById(999L))
                .thenReturn(false);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> usuarioService.eliminarUsuario(999L)
        );

        assertEquals(
                "User not found with id: 999",
                exception.getMessage()
        );
    }

    /**
     * Creates a sample user for testing.
     *
     * @return a sample user
     */
    private Usuario crearUsuario() {
        Usuario usuario = new Usuario();
        usuario.setNombre("Juan");
        usuario.setApellido("Camacho");
        usuario.setEmail("juan@example.com");
        usuario.setPassword("LearnHub123");
        usuario.setRol(Rol.ESTUDIANTE);
        usuario.setActivo(true);

        return usuario;
    }
}