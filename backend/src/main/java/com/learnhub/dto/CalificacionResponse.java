package com.learnhub.dto;

/**
 * Response data for Calificacion.
 */
public class CalificacionResponse {

    /**
     * Grade identifier.
     */
    private Long id;

    /**
     * Identifier of the user receiving the grade.
     */
    private Long usuarioId;

    /**
     * Identifier of the evaluation.
     */
    private Long evaluacionId;

    /**
     * Grade obtained.
     */
    private Double nota;

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