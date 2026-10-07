package com.learnhub.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

/**
 * Request data for creating or updating a Modulo.
 */
public class ModuloRequest {

    /**
     * Module name.
     */
    @NotBlank(message = "The module name is required")
    private String nombre;

    /**
     * Module description.
     */
    private String descripcion;

    /**
     * Module order inside the course.
     */
    @NotNull(message = "The module order is required")
    @PositiveOrZero(message = "The module order cannot be negative")
    private Integer orden;

    /**
     * Identifier of the course.
     */
    @NotNull(message = "The course is required")
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