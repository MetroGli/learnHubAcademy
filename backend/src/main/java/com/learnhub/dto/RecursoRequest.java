package com.learnhub.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class RecursoRequest {

    @NotBlank(message = "El nombre del recurso es obligatorio")
    private String nombre;

    @NotBlank(message = "El tipo de recurso es obligatorio")
    private String tipo;

    @NotBlank(message = "La URL del recurso es obligatoria")
    private String url;

    @NotNull(message = "La lección es obligatoria")
    private Long leccionId;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Long getLeccionId() {
        return leccionId;
    }

    public void setLeccionId(Long leccionId) {
        this.leccionId = leccionId;
    }
}