package com.learnhub.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final String PREFIJO_BEARER = "Bearer ";

    private final JwtService jwtService;
    private final CustomUserDetailsService userDetailsService;
    private final TokenCookie tokenCookie;

    public JwtAuthenticationFilter(
            JwtService jwtService,
            CustomUserDetailsService userDetailsService,
            TokenCookie tokenCookie) {

        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
        this.tokenCookie = tokenCookie;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)
            throws ServletException, IOException {

        Optional<String> token = obtenerToken(request);

        if (token.isPresent()
                && SecurityContextHolder.getContext().getAuthentication() == null) {
            autenticar(token.get(), request);
        }

        filterChain.doFilter(request, response);
    }

    /**
     * La app web usa la cookie. El encabezado Authorization se mantiene
     * para probar la API con curl o Postman.
     */
    private Optional<String> obtenerToken(HttpServletRequest request) {
        Optional<String> desdeCookie = tokenCookie.leer(request);

        if (desdeCookie.isPresent()) {
            return desdeCookie;
        }

        String encabezado = request.getHeader("Authorization");

        if (encabezado != null && encabezado.startsWith(PREFIJO_BEARER)) {
            return Optional.of(encabezado.substring(PREFIJO_BEARER.length()));
        }

        return Optional.empty();
    }

    private void autenticar(String token, HttpServletRequest request) {
        try {
            String username = jwtService.extractUsername(token);
            UserDetails userDetails =
                    userDetailsService.loadUserByUsername(username);

            if (!userDetails.isEnabled()
                    || !jwtService.isTokenValid(token, userDetails)) {
                return;
            }

            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            userDetails.getAuthorities());

            authentication.setDetails(
                    new WebAuthenticationDetailsSource().buildDetails(request));

            SecurityContextHolder.getContext().setAuthentication(authentication);

        } catch (UsernameNotFoundException | io.jsonwebtoken.JwtException
                 | IllegalArgumentException e) {
            // Token inválido, vencido o de un usuario eliminado:
            // la petición sigue sin autenticar y Spring responde 401/403.
        }
    }
}
