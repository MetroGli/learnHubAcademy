package com.learnhub.exception;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

  @RestControllerAdvice
  public class GlobalExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, Object>> handleIllegalArgumentException(
    IllegalArgumentException exception) {

    Map<String, Object> error = new LinkedHashMap<>();

    error.put("status", HttpStatus.NOT_FOUND.value());
    error.put("error", "Not Found");
    error.put("message", exception.getMessage());
    error.put("timestamp", LocalDateTime.now());

    return ResponseEntity
    .status(HttpStatus.NOT_FOUND)
    .body(error);
    }
    }
