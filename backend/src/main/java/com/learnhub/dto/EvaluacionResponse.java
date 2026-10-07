package com.learnhub.dto;

/**
 * Response data for Evaluacion.
 */
public class EvaluacionResponse {

    /**
     * Evaluation identifier.
     */
    private Long id;

    /**
     * Evaluation title.
     */
    private String titulo;

    /**
     * Evaluation description.
     */
    private String descripcion;

    /**
     * Percentage assigned to the evaluation.
     */
    private Double porcentaje;

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

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Double getPorcentaje() {
        return porcentaje;
    }

    public void setPorcentaje(Double porcentaje) {
        this.porcentaje = porcentaje;
    }

    public Long getCursoId() {
        return cursoId;
    }

    public void setCursoId(Long cursoId) {
        this.cursoId = cursoId;
    }
}