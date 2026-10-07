package com.learnhub.auth;

import com.learnhub.entity.Usuario;
import com.learnhub.repository.UsuarioRepository;
import com.learnhub.security.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

/**
 * Provides authentication business logic for LearnHub users.
 */
@Service
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final UsuarioRepository usuarioRepository;
    private final JwtService jwtService;

    /**
     * Creates the authentication service.
     *
     * @param authenticationManager manager used to authenticate credentials
     * @param usuarioRepository repository used to retrieve users
     * @param jwtService service used to generate JWT tokens
     */
    public AuthService(
            AuthenticationManager authenticationManager,
            UsuarioRepository usuarioRepository,
            JwtService jwtService) {

        this.authenticationManager = authenticationManager;
        this.usuarioRepository = usuarioRepository;
        this.jwtService = jwtService;
    }

    /**
     * Authenticates a user and generates a JWT.
     *
     * @param request login credentials
     * @return authenticated user information and JWT token
     */
    public LoginResponse autenticar(LoginRequest request) {

        Authentication authentication =
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                request.getEmail(),
                                request.getPassword()
                        )
                );

        String email = authentication.getName();

        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException(
                        "User not found with email: " + email));

        String token = jwtService.generateToken(
                (org.springframework.security.core.userdetails.UserDetails)
                        authentication.getPrincipal()
        );

        LoginResponse response = new LoginResponse();

        response.setId(usuario.getId());
        response.setNombre(usuario.getNombre());
        response.setApellido(usuario.getApellido());
        response.setEmail(usuario.getEmail());
        response.setRol(usuario.getRol());
        response.setToken(token);

        return response;
    }
}