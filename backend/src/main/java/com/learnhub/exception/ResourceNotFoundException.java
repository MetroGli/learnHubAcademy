package com.learnhub.exception;

/**

* Exception thrown when a requested resource does not exist.
  */
  public class ResourceNotFoundException extends RuntimeException {

  /**

  * Creates a resource not found exception.
  *
  * @param message description of the missing resourc
    */
    public ResourceNotFoundException(String message) {
    super(message);
    }
    }
