package com.learnhub.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http)
            throws Exception {

        http
            .csrf(csrf -> csrf.disable())
            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(
                    "/api/health",
                    "/api/auth/**"
                ).permitAll()
                .requestMatchers("/api/usuarios/**")
                    .hasRole("ADMINISTRADOR")
                .requestMatchers("/api/estudiantes/**")
                    .hasAnyRole("ADMINISTRADOR", "PROFESOR")
                // Avance propio (lecciones completadas y presentar evaluaciones)
                .requestMatchers("/api/mi-avance/**").authenticated()
                // Contenido de los cursos: lo leen todos, lo gestionan profesores y administradores
                .requestMatchers(HttpMethod.GET, "/api/**").authenticated()
                .requestMatchers(
                    "/api/cursos/**",
                    "/api/modulos/**",
                    "/api/lecciones/**",
                    "/api/recursos/**",
                    "/api/evaluaciones/**",
                    "/api/preguntas/**",
                    "/api/respuestas/**"
                ).hasAnyRole("ADMINISTRADOR", "PROFESOR")
                // Notas y progreso: el estudiante ya no los escribe directamente,
                // los calcula el servidor desde /api/mi-avance
                .requestMatchers(
                    "/api/progresos/**",
                    "/api/calificaciones/**"
                ).hasAnyRole("ADMINISTRADOR", "PROFESOR")
                // El estudiante se inscribe solo (lo valida UsuarioActual), pero no borra inscripciones
                .requestMatchers(HttpMethod.DELETE, "/api/inscripciones/**")
                    .hasAnyRole("ADMINISTRADOR", "PROFESOR")
                .anyRequest().authenticated()
            )
            .exceptionHandling(e -> e
                .authenticationEntryPoint(
                    new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)))
            .addFilterBefore(
                jwtAuthenticationFilter,
                UsernamePasswordAuthenticationFilter.class
            );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration configuration) throws Exception {

        return configuration.getAuthenticationManager();
    }
}