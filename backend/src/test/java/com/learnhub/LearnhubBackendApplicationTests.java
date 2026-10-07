package com.learnhub;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

/**
 * Tests the application context.
 */
@SpringBootTest
@ActiveProfiles("test")
class LearnhubBackendApplicationTests {

    /**
     * Verifies that the application context loads successfully.
     */
    @Test
    void contextLoads() {
    }
}