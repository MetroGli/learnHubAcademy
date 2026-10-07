package com.learnhub.dto;

import com.learnhub.entity.Rol;

/**
 * Represents the data returned by the user API.
 *
 * <p>The password is intentionally excluded from this response.</p>
 */
public class UsuarioResponse {

    /**
     * User identifier.
     */
    private Long id;

    /**
     * User's first name.
     */
    private String nombre;

    /**
     * User's last name.
     */
    private String apellido;

    /**
     * User's email address.
     */
    private String email;

    /**
     * Role assigned to the user.
     */
    private Rol rol;

    /**
     * Indicates whether the user is active.
     */
    private boolean activo;

    /**
     * Returns the user identifier.
     *
     * @return the user identifier
     */
    public Long getId() {
        return id;
    }

    /**
     * Updates the user identifier.
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