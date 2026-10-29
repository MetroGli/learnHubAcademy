package com.learnhub.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

/**
 * Registro de que un estudiante terminó una lección. Con estas filas se
 * calcula el porcentaje de avance de cada curso.
 */
@Entity
@Table(
        name = "lecciones_completadas",
        uniqueConstraints = @UniqueConstraint(
                name = "uk_leccion_completada",
                columnNames = {"usuario_id", "leccion_id"}))
public class LeccionCompletada {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "usuario_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Usuario usuario;

    // Si se borra la lección, sus registros se borran solos en la base de datos.
    @ManyToOne(optional = false)
    @JoinColumn(name = "leccion_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Leccion leccion;

    @Column(nullable = false)
    private LocalDate fecha;

    public Long getId() {
        return id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Leccion getLeccion() {
        return leccion;
    }

    public void setLeccion(Leccion leccion) {
        this.leccion = leccion;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }
}
