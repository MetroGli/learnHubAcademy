package com.learnhub.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * El campo correcta va en null (y no se envía) cuando quien consulta es un estudiante.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RespuestaResponse {

    private Long id;

    private String texto;

    @JsonProperty("correcta")
    private Boolean correcta;

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

    public Boolean isCorrecta() {
        return correcta;
    }

    public void setCorrecta(Boolean correcta) {
        this.correcta = correcta;
    }

    public Long getPreguntaId() {
        return preguntaId;
    }

    public void setPreguntaId(Long preguntaId) {
        this.preguntaId = preguntaId;
    }
}