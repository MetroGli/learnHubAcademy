package com.learnhub.dto;

import com.learnhub.entity.Rol;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * Represents the data required to create or update a user.
 */
public class UsuarioRequest {

    /**
     * User's first name.
     */
    @NotBlank(message = "The first name is required")
    @Size(max = 100, message = "The first name cannot exceed 100 characters")
    private String nombre;

    /**
     * User's last name.
     */
    @NotBlank(message = "The last name is required")
    @Size(max = 100, message = "The last name cannot exceed 100 characters")
    private String apellido;

    /**
     * User's email address.
     */
    @NotBlank(message = "The email is required")
    @Email(message = "The email must be valid")
    @Size(max = 150, message = "The email cannot exceed 150 characters")
    private String email;

    /**
     * User's password.
     */
    @NotBlank(message = "The password is required")
    @Size(min = 8, message = "The password must contain at least 8 characters")
    private String password;

    /**
     * Role assigned to the user.
     */
    @NotNull(message = "The role is required")
    private Rol rol;

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
}