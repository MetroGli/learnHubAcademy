package com.learnhub.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class PreguntaRequest {

    @NotBlank(message = "El enunciado de la pregunta es obligatorio")
    private String enunciado;

    @NotBlank(message = "El tipo de pregunta es obligatorio")
    private String tipo;

    @NotNull(message = "La evaluación es obligatoria")
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