package com.learnhub.dto;

import java.time.LocalDate;

/**
 * Response data for Inscripcion.
 */
public class InscripcionResponse {

    /**
     * Enrollment identifier.
     */
    private Long id;

    /**
     * Identifier of the user.
     */
    private Long usuarioId;

    /**
     * Identifier of the course.
     */
    private Long cursoId;

    /**
     * Enrollment date.
     */
    private LocalDate fechaInscripcion;

    /**
     * Indicates whether the enrollment is active.
     */
    private boolean activa;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public LocalDate getFechaInscripcion() {
        return fechaInscripcion;
    }

    public void setFechaInscripcion(LocalDate fechaInscripcion) {
        this.fechaInscripcion = fechaInscripcion;
    }

    public boolean isActiva() {
        return activa;
    }

    public void setActiva(boolean activa) {
        this.activa = activa;
    }
}