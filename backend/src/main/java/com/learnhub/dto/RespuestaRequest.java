package com.learnhub.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * Request data for creating or updating a Respuesta.
 */
public class RespuestaRequest {

    /**
     * Answer text.
     */
    @NotBlank(message = "The answer text is required")
    private String texto;

    /**
     * Indicates whether the answer is correct.
     */
    private boolean correcta;

    /**
     * Identifier of the question.
     */
    @NotNull(message = "The question is required")
    private Long preguntaId;

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public boolean isCorrecta() {
        return correcta;
    }

    public void setCorrecta(boolean correcta) {
        this.correcta = correcta;
    }

    public Long getPreguntaId() {
        return preguntaId;
    }

    public void setPreguntaId(Long preguntaId) {
        this.preguntaId = preguntaId;
    }
}
