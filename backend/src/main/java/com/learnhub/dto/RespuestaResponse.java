package com.learnhub.dto;

/**
 * Response data for Respuesta.
 */
public class RespuestaResponse {

    /**
     * Answer identifier.
     */
    private Long id;

    /**
     * Answer text.
     */
    private String texto;

    /**
     * Indicates whether the answer is correct.
     */
    private boolean correcta;

    /**
     * Identifier of the question.
     */
    private Long preguntaId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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