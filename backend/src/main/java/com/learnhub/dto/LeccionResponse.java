package com.learnhub.dto;

/**
 * Response data for Leccion.
 */
public class LeccionResponse {

    /**
     * Lesson identifier.
     */
    private Long id;

    /**
     * Lesson title.
     */
    private String titulo;

    /**
     * Lesson content.
     */
    private String contenido;

    /**
     * Lesson order.
     */
    private Integer orden;

    /**
     * Identifier of the module.
     */
    private Long moduloId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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