package com.learnhub.dto;

/**
 * Response data for Curso.
 */
public class CursoResponse {

    /**
     * Course identifier.
     */
    private Long id;

    /**
     * Course name.
     */
    private String nombre;

    /**
     * Course description.
     */
    private String descripcion;

    /**
     * Indicates whether the course is active.
     */
    private boolean activo;

    /**
     * Identifier of the professor responsible for the course.
     */
    private Long profesorId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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