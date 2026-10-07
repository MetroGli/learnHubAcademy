package com.learnhub.dto;

import jakarta.validation.constraints.NotNull;

/**
 * Request data for creating or updating an Inscripcion.
 */
public class InscripcionRequest {

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
     * Indicates whether the enrollment is active.
     */
    private boolean activa = true;

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

    public boolean isActiva() {
        return activa;
    }

    public void setActiva(boolean activa) {
        this.activa = activa;
    }
}