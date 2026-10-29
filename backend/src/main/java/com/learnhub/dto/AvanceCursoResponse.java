package com.learnhub.dto;

import java.util.List;

/**
 * Avance del usuario en un curso: porcentaje y lecciones que ya completó.
 */
public record AvanceCursoResponse(
        Long cursoId,
        double porcentaje,
        int totalLecciones,
        List<Long> leccionesCompletadas) {
}
