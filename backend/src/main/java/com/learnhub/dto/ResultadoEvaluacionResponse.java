package com.learnhub.dto;

import java.util.List;

/**
 * Resultado de una evaluación calificada por el servidor.
 */
public record ResultadoEvaluacionResponse(
        Long evaluacionId,
        double nota,
        int correctas,
        int total,
        List<DetallePregunta> detalle) {

    public record DetallePregunta(
            Long preguntaId,
            Long respuestaElegidaId,
            Long respuestaCorrectaId,
            boolean acerto) {
    }
}
