package com.learnhub.security;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import java.time.Duration;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;
import org.springframework.web.util.WebUtils;

/**
 * Maneja la cookie donde viaja el JWT. Es HttpOnly para que ningún script
 * de la página pueda leer el token (protege contra robo por XSS).
 */
@Component
public class TokenCookie {

    public static final String NOMBRE = "learnhub_token";
    private static final String RUTA = "/api";

    private final Duration duracion;
    private final boolean segura;

    public TokenCookie(
            @Value("${jwt.expiration}") long expiracionMs,
            @Value("${jwt.cookie-secure:true}") boolean segura) {

        this.duracion = Duration.ofMillis(expiracionMs);
        this.segura = segura;
    }

    public ResponseCookie crear(String token) {
        return base(token).maxAge(duracion).build();
    }

    public ResponseCookie eliminar() {
        return base("").maxAge(0).build();
    }

    public Optional<String> leer(HttpServletRequest request) {
        Cookie cookie = WebUtils.getCookie(request, NOMBRE);

        if (cookie == null || cookie.getValue().isBlank()) {
            return Optional.empty();
        }

        return Optional.of(cookie.getValue());
    }

    private ResponseCookie.ResponseCookieBuilder base(String valor) {
        return ResponseCookie.from(NOMBRE, valor)
                .httpOnly(true)
                .secure(segura)
                .sameSite("Strict")
                .path(RUTA);
    }
}
