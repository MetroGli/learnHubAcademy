package com.learnhub.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * Request data for creating or updating a Curso.
 */
public class CursoRequest {

    /**
     * Course name.
     */
    @NotBlank(message = "The course name is required")
    @Size(max = 255, message = "The course name cannot exceed 255 characters")
    private String nombre;

    /**
     * Course description.
     */
    private String descripcion;

    /**
     * Indicates whether the course is active.
     */
    private boolean activo = true;

    /**
     * Identifier of the professor responsible for the course.
     */
    @NotNull(message = "The professor is required")
    private Long profesorId;

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

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public Long getProfesorId() {
        return profesorId;
    }

    public void setProfesorId(Long profesorId) {
        this.profesorId = profesorId;
    }
}