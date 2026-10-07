package com.learnhub.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * Request data for creating or updating a Recurso.
 */
public class RecursoRequest {

    /**
     * Resource name.
     */
    @NotBlank(message = "The resource name is required")
    private String nombre;

    /**
     * Resource type.
     */
    @NotBlank(message = "The resource type is required")
    private String tipo;

    /**
     * Resource URL.
     */
    @NotBlank(message = "The resource URL is required")
    private String url;

    /**
     * Identifier of the lesson.
     */
    @NotNull(message = "The lesson is required")
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