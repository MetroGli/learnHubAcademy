package com.learnhub.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

/**
 * Represents the credentials required to authenticate a user.
 */
public class LoginRequest {

    /**
     * User email address.
     */
    @NotBlank(message = "The email is required")
    @Email(message = "The email must be valid")
    private String email;

    /**
     * User password.
     */
    @NotBlank(message = "The password is required")
    private String password;

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
}