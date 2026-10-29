package com.learnhub.dto;

import jakarta.validation.constraints.NotNull;
import java.util.Map;

/**
 * Respuestas que marcó el estudiante: id de la pregunta -> id de la opción elegida.
 */
public record PresentarEvaluacionRequest(
        @NotNull(message = "Debes enviar tus respuestas") Map<Long, Long> respuestas) {
}
