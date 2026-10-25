package com.learnhub.auth;

import com.learnhub.security.TokenCookie;
import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;
    private final TokenCookie tokenCookie;

    public AuthController(AuthService authService, TokenCookie tokenCookie) {
        this.authService = authService;
        this.tokenCookie = tokenCookie;
    }

    /**
     * El token se entrega en una cookie HttpOnly y no en el cuerpo,
     * así el frontend nunca lo maneja ni lo guarda en localStorage.
     */
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(
            @Valid @RequestBody LoginRequest request) {

        SesionIniciada sesion = authService.autenticar(request);

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE,
                        tokenCookie.crear(sesion.token()).toString())
                .body(sesion.usuario());
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout() {
        return ResponseEntity.noContent()
                .header(HttpHeaders.SET_COOKIE,
                        tokenCookie.eliminar().toString())
                .build();
    }
}
