package com.learnhub.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

/**
 * Request data for creating or updating a Progreso.
 */
public class ProgresoRequest {

    /**
     * Identifier of the user.
     */
    @NotNull(message = "The user is required")
    private Long usuarioId;

    /**
     * Identifier of the course.
     */
    @NotNull(message = "The course is required")
    private Long cursoId;

    /**
     * Course completion percentage.
     */
    @NotNull(message = "The percentage is required")
    @PositiveOrZero(message = "The percentage cannot be negative")
    private Double porcentaje;

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    public Long getCursoId() {
        return cursoId;
    }

    public void setCursoId(Long cursoId) {
        this.cursoId = cursoId;
    }

    public Double getPorcentaje() {
        return porcentaje;
    }

    public void setPorcentaje(Double porcentaje) {
        this.porcentaje = porcentaje;
    }
}