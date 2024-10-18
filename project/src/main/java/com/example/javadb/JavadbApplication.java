package com.example.javadb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Entry point for the application.
 */
@SpringBootApplication
public class JavadbApplication {

    // Private constructor to prevent instantiation
    private JavadbApplication() {
        throw new UnsupportedOperationException(
                "This class cannot be instantiated.");
    }

    /**
     * Main method to run the application.
     *
     * @param args the command line arguments
     *             (must be final according to style check)
     */
    public static void main(final String[] args) {
        SpringApplication.run(JavadbApplication.class, args);
    }
}
