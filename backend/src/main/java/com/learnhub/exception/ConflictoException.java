package com.learnhub.exception;

/**
 * El dato que se intenta guardar choca con uno existente (responde 409).
 */
public class ConflictoException extends RuntimeException {

    public ConflictoException(String mensaje) {
        super(mensaje);
    }
}
