package com.learnhub.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

/**
 * Request data for creating or updating a Leccion.
 */
public class LeccionRequest {

    /**
     * Lesson title.
     */
    @NotBlank(message = "The lesson title is required")
    private String titulo;

    /**
     * Lesson content.
     */
    private String contenido;

    /**
     * Lesson order inside the module.
     */
    @NotNull(message = "The lesson order is required")
    @PositiveOrZero(message = "The lesson order cannot be negative")
    private Integer orden;

    /**
     * Identifier of the module.
     */
    @NotNull(message = "The module is required")
    private Long moduloId;

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public Integer getOrden() {
        return orden;
    }

    public void setOrden(Integer orden) {
        this.orden = orden;
    }

    public Long getModuloId() {
        return moduloId;
    }

    public void setModuloId(Long moduloId) {
        this.moduloId = moduloId;
    }
}