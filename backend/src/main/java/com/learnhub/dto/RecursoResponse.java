package com.learnhub.dto;

/**
 * Response data for Recurso.
 */
public class RecursoResponse {

    /**
     * Resource identifier.
     */
    private Long id;

    /**
     * Resource name.
     */
    private String nombre;

    /**
     * Resource type.
     */
    private String tipo;

    /**
     * Resource URL.
     */
    private String url;

    /**
     * Identifier of the lesson.
     */
    private Long leccionId;

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