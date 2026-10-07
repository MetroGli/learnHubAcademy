package com.learnhub.controller;

import com.learnhub.entity.Usuario;
import com.learnhub.repository.UsuarioRepository;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Permite que cualquier usuario autenticado consulte y edite su propio perfil.
 *
 * A diferencia de /api/usuarios (solo ADMINISTRADOR), aquí el usuario se
 * identifica por su token, así que nadie puede modificar la cuenta de otra
 * persona. Las validaciones se hacen a mano y las respuestas de error se
 * devuelven directamente para que el frontend reciba un mensaje claro.
 */
@RestController
@RequestMapping("/api/perfil")
public class PerfilController {

    private static final int MAX_NOMBRE = 100;
    private static final int MIN_PASSWORD = 8;

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public PerfilController(
            UsuarioRepository usuarioRepository,
            PasswordEncoder passwordEncoder) {

        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Devuelve los datos del usuario autenticado.
     */
    @GetMapping
    public ResponseEntity<Map<String, Object>> obtenerPerfil(
            Authentication authentication) {

        Optional<Usuario> usuario =
                usuarioRepository.findByEmail(authentication.getName());

        if (usuario.isEmpty()) {
            return error(HttpStatus.NOT_FOUND, "Usuario no encontrado.");
        }

        return ResponseEntity.ok(aMapa(usuario.get()));
    }

    /**
     * Actualiza el nombre y el apellido del usuario autenticado.
     */
    @PutMapping
    public ResponseEntity<Map<String, Object>> actualizarPerfil(
            Authentication authentication,
            @RequestBody Map<String, String> cuerpo) {

        String nombre = limpiar(cuerpo.get("nombre"));
        String apellido = limpiar(cuerpo.get("apellido"));

        if (nombre.isEmpty() || apellido.isEmpty()) {
            return error(
                    HttpStatus.BAD_REQUEST,
                    "El nombre y el apellido son obligatorios.");
        }

        if (nombre.length() > MAX_NOMBRE || apellido.length() > MAX_NOMBRE) {
            return error(
                    HttpStatus.BAD_REQUEST,
                    "El nombre y el apellido no pueden superar los "
                            + MAX_NOMBRE + " caracteres.");
        }

        Optional<Usuario> encontrado =
                usuarioRepository.findByEmail(authentication.getName());

        if (encontrado.isEmpty()) {
            return error(HttpStatus.NOT_FOUND, "Usuario no encontrado.");
        }

        Usuario usuario = encontrado.get();
        usuario.setNombre(nombre);
        usuario.setApellido(apellido);

        return ResponseEntity.ok(aMapa(usuarioRepository.save(usuario)));
    }

    /**
     * Cambia la contraseña del usuario autenticado, verificando la actual.
     */
    @PutMapping("/password")
    public ResponseEntity<Map<String, Object>> cambiarPassword(
            Authentication authentication,
            @RequestBody Map<String, String> cuerpo) {

        String passwordActual = cuerpo.get("passwordActual");
        String passwordNueva = cuerpo.get("passwordNueva");

        if (passwordActual == null || passwordActual.isEmpty()) {
            return error(
                    HttpStatus.BAD_REQUEST,
                    "Debes escribir tu contraseña actual.");
        }

        if (passwordNueva == null || passwordNueva.length() < MIN_PASSWORD) {
            return error(
                    HttpStatus.BAD_REQUEST,
                    "La nueva contraseña debe tener al menos "
                            + MIN_PASSWORD + " caracteres.");
        }

        Optional<Usuario> encontrado =
                usuarioRepository.findByEmail(authentication.getName());

        if (encontrado.isEmpty()) {
            return error(HttpStatus.NOT_FOUND, "Usuario no encontrado.");
        }

        Usuario usuario = encontrado.get();

        if (!passwordEncoder.matches(passwordActual, usuario.getPassword())) {
            return error(
                    HttpStatus.BAD_REQUEST,
                    "La contraseña actual no es correcta.");
        }

        if (passwordEncoder.matches(passwordNueva, usuario.getPassword())) {
            return error(
                    HttpStatus.BAD_REQUEST,
                    "La nueva contraseña debe ser diferente de la actual.");
        }

        usuario.setPassword(passwordEncoder.encode(passwordNueva));
        usuarioRepository.save(usuario);

        Map<String, Object> respuesta = new LinkedHashMap<>();
        respuesta.put("message", "Contraseña actualizada correctamente.");

        return ResponseEntity.ok(respuesta);
    }

    private String limpiar(String texto) {
        return texto == null ? "" : texto.trim();
    }

    private Map<String, Object> aMapa(Usuario usuario) {
        Map<String, Object> datos = new LinkedHashMap<>();
        datos.put("id", usuario.getId());
        datos.put("nombre", usuario.getNombre());
        datos.put("apellido", usuario.getApellido());
        datos.put("email", usuario.getEmail());
        datos.put("rol", usuario.getRol());
        return datos;
    }

    private ResponseEntity<Map<String, Object>> error(
            HttpStatus estado,
            String mensaje) {

        Map<String, Object> cuerpo = new LinkedHashMap<>();
        cuerpo.put("status", estado.value());
        cuerpo.put("error", estado.getReasonPhrase());
        cuerpo.put("message", mensaje);
        cuerpo.put("timestamp", LocalDateTime.now());

        return ResponseEntity.status(estado).body(cuerpo);
    }
}
