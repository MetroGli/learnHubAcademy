package com.learnhub.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public class ModuloRequest {

    @NotBlank(message = "El nombre del módulo es obligatorio")
    private String nombre;

    private String descripcion;

    @NotNull(message = "El orden del módulo es obligatorio")
    @PositiveOrZero(message = "El orden del módulo no puede ser negativo")
    private Integer orden;

    @NotNull(message = "El curso es obligatorio")
    private Long cursoId;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getOrden() {
        return orden;
    }

    public void setOrden(Integer orden) {
        this.orden = orden;
    }

    public Long getCursoId() {
        return cursoId;
    }

    public void setCursoId(Long cursoId) {
        this.cursoId = cursoId;
    }
}