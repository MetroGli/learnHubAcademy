package com.learnhub.exception;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, Object>> noEncontrado(
            IllegalArgumentException exception) {

        return respuesta(HttpStatus.NOT_FOUND, exception.getMessage());
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<Map<String, Object>> estadoInvalido(
            IllegalStateException exception) {

        return respuesta(HttpStatus.BAD_REQUEST, exception.getMessage());
    }

    @ExceptionHandler(ConflictoException.class)
    public ResponseEntity<Map<String, Object>> conflicto(
            ConflictoException exception) {

        return respuesta(HttpStatus.CONFLICT, exception.getMessage());
    }

    // Respaldo por si dos peticiones simultáneas pasan la validación del servicio.
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Map<String, Object>> integridad(
            DataIntegrityViolationException exception) {

        return respuesta(HttpStatus.CONFLICT,
                "El registro ya existe o hace referencia a datos inválidos.");
    }

    private ResponseEntity<Map<String, Object>> respuesta(
            HttpStatus estado, String mensaje) {

        Map<String, Object> error = new LinkedHashMap<>();
        error.put("status", estado.value());
        error.put("error", estado.getReasonPhrase());
        error.put("message", mensaje);
        error.put("timestamp", LocalDateTime.now());

        return ResponseEntity.status(estado).body(error);
    }
}
