package com.learnhub.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

/**
 * Request data for creating or updating an Evaluacion.
 */
public class EvaluacionRequest {

    /**
     * Evaluation title.
     */
    @NotBlank(message = "The evaluation title is required")
    private String titulo;

    /**
     * Evaluation description.
     */
    private String descripcion;

    /**
     * Percentage assigned to the evaluation.
     */
    @NotNull(message = "The percentage is required")
    @PositiveOrZero(message = "The percentage cannot be negative")
    private Double porcentaje;

    /**
     * Identifier of the course.
     */
    @NotNull(message = "The course is required")
    private Long cursoId;

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