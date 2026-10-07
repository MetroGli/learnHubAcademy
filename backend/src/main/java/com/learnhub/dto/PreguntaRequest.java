package com.learnhub.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * Request data for creating or updating a Pregunta.
 */
public class PreguntaRequest {

    /**
     * Question statement.
     */
    @NotBlank(message = "The question statement is required")
    private String enunciado;

    /**
     * Question type.
     */
    @NotBlank(message = "The question type is required")
    private String tipo;

    /**
     * Identifier of the evaluation.
     */
    @NotNull(message = "The evaluation is required")
    private Long evaluacionId;

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