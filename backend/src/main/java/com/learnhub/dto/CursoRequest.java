package com.learnhub.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

public class CursoRequest {

    @NotBlank(message = "El nombre del curso es obligatorio")
    @Size(max = 255, message = "El nombre del curso no puede superar los 255 caracteres")
    private String nombre;

    private String descripcion;

    private boolean activo = true;

    @NotNull(message = "El profesor es obligatorio")
    private Long profesorId;

    @Size(max = 60, message = "La categoría no puede superar los 60 caracteres")
    private String categoria;

    @Pattern(regexp = "BASICO|INTERMEDIO|AVANZADO", message = "El nivel debe ser BASICO, INTERMEDIO o AVANZADO")
    private String nivel;

    @PositiveOrZero(message = "La duración no puede ser negativa")
    private Integer duracionHoras;

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

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getNivel() {
        return nivel;
    }

    public void setNivel(String nivel) {
        this.nivel = nivel;
    }

    public Integer getDuracionHoras() {
        return duracionHoras;
    }

    public void setDuracionHoras(Integer duracionHoras) {
        this.duracionHoras = duracionHoras;
    }
}
