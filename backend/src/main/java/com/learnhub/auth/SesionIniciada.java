package com.learnhub.auth;

/**
 * Resultado de un login correcto: el token (que va en la cookie)
 * y los datos del usuario (que van en el cuerpo de la respuesta).
 */
public record SesionIniciada(String token, LoginResponse usuario) {
}
