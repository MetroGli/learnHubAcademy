package com.learnhub.dto;

/**
 * Response data for Modulo.
 */
public class ModuloResponse {

    /**
     * Module identifier.
     */
    private Long id;

    /**
     * Module name.
     */
    private String nombre;

    /**
     * Module description.
     */
    private String descripcion;

    /**
     * Module order.
     */
    private Integer orden;

    /**
     * Identifier of the course.
     */
    private Long cursoId;

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