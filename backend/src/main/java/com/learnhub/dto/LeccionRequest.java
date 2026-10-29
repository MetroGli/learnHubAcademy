package com.learnhub.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public class LeccionRequest {

    @NotBlank(message = "El título de la lección es obligatorio")
    private String titulo;

    private String contenido;

    @NotNull(message = "El orden de la lección es obligatorio")
    @PositiveOrZero(message = "El orden de la lección no puede ser negativo")
    private Integer orden;

    @NotNull(message = "El módulo es obligatorio")
    private Long moduloId;

    @PositiveOrZero(message = "La duración no puede ser negativa")
    private Integer duracionMinutos;

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

    public Integer getDuracionMinutos() {
        return duracionMinutos;
    }

    public void setDuracionMinutos(Integer duracionMinutos) {
        this.duracionMinutos = duracionMinutos;
    }
}
