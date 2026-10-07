package com.learnhub.auth;

import com.learnhub.entity.Rol;

/**
 * Represents the response returned after successful authentication.
 */
public class LoginResponse {

    private Long id;
    private String nombre;
    private String apellido;
    private String email;
    private Rol rol;
    private String token;


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
 * Returns the JWT token.
 *
 * @return JWT token
 */
    public String getToken() {
        return token;
    }

    /**
     * Updates the JWT token.
     *
     * @param token JWT token
     */
    public void setToken(String token) {
        this.token = token;
    }
}