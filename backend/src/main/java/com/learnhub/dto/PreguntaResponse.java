package com.learnhub.dto;

/**
 * Response data for Pregunta.
 */
public class PreguntaResponse {

    /**
     * Question identifier.
     */
    private Long id;

    /**
     * Question statement.
     */
    private String enunciado;

    /**
     * Question type.
     */
    private String tipo;

    /**
     * Identifier of the evaluation.
     */
    private Long evaluacionId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEnunciado() {
        return enunciado;
    }

    public void setEnunciado(String enunciado) {
        this.enunciado = enunciado;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Long getEvaluacionId() {
        return evaluacionId;
    }

    public void setEvaluacionId(Long evaluacionId) {
        this.evaluacionId = evaluacionId;
    }
}