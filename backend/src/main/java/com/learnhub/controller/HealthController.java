package com.learnhub.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Provides endpoints for checking the availability of the LearnHub API.
 */
@RestController
public class HealthController {

    /**
     * Checks whether the LearnHub backend is running.
     *
     * @return a message indicating that the API is operational
     */
    @GetMapping("/api/health")
    public String healthCheck() {
        return "LearnHub API is running";
    }
}