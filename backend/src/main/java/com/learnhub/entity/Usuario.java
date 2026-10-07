package com.learnhub.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * Represents a user registered in the LearnHub platform.
 */
@Entity
@Table(name = "usuarios")
public class Usuario {

    /**
     * Unique identifier of the user.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * User's first name.
     */
    @Column(nullable = false, length = 100)
    private String nombre;

    /**
     * User's last name.
     */
    @Column(nullable = false, length = 100)
    private String apellido;

    /**
     * User's email address.
     */
    @Column(nullable = false, unique = true, length = 150)
    private String email;

    /**
     * User's password.
     */
    @Column(nullable = false)
    private String password;

    /**
     * Role assigned to the user.
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Rol rol;

    /**
     * Indicates whether the user is active.
     */
    @Column(nullable = false)
    private boolean activo;

    /**
     * Creates an empty user.
     */
    public Usuario() {
    }

    /**
     * Returns the user's identifier.
     *
     * @return the user identifier
     */
    public Long getId() {
        return id;
    }

    /**
     * Updates the user's identifier.
     *
     * @param id the user identifier
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Returns the user's first name.
     *
     * @return the first name
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Updates the user's first name.
     *
     * @param nombre the first name
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Returns the user's last name.
     *
     * @return the last name
     */
    public String getApellido() {
        return apellido;
    }

    /**
     * Updates the user's last name.
     *
     * @param apellido the last name
     */
    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    /**
     * Returns the user's email address.
     *
     * @return the email address
     */
    public String getEmail() {
        return email;
    }

    /**
     * Updates the user's email address.
     *
     * @param email the email address
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Returns the user's password.
     *
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Updates the user's password.
     *
     * @param password the password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Returns the user's role.
     *
     * @return the assigned role
     */
    public Rol getRol() {
        return rol;
    }

    /**
     * Updates the user's role.
     *
     * @param rol the assigned role
     */
    public void setRol(Rol rol) {
        this.rol = rol;
    }

    /**
     * Returns whether the user is active.
     *
     * @return true if the user is active
     */
    public boolean isActivo() {
        return activo;
    }

    /**
     * Updates the user's active status.
     *
     * @param activo the active status
     */
    public void setActivo(boolean activo) {
        this.activo = activo;
    }
}