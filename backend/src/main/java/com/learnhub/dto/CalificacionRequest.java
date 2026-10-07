package com.learnhub.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

/**
 * Request data for creating or updating a Calificacion.
 */
public class CalificacionRequest {

    /**
     * Identifier of the user receiving the grade.
     */
    @NotNull(message = "The user is required")
    private Long usuarioId;

    /**
     * Identifier of the evaluation being graded.
     */
    @NotNull(message = "The evaluation is required")
    private Long evaluacionId;

    /**
     * Grade obtained by the user.
     */
    @NotNull(message = "The grade is required")
    @PositiveOrZero(message = "The grade cannot be negative")
    private Double nota;

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    public Long getEvaluacionId() {
        return evaluacionId;
    }

    public void setEvaluacionId(Long evaluacionId) {
        this.evaluacionId = evaluacionId;
    }

    public Double getNota() {
        return nota;
    }

    public void setNota(Double nota) {
        this.nota = nota;
    }
}