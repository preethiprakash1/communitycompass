package com.example.javadb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Entry point for the application.
 */

@SpringBootApplication
@SuppressWarnings("checkstyle:HideUtilityClassConstructorCheck") // Suppressing PMD warning because
// this is the entry point of the application
public class JavadbApplication {
    /**
     * Main method to run the application.
     *
     * @param args the command line arguments
     */
    public static void main(final String[] args) {
        SpringApplication.run(JavadbApplication.class, args);
    }
}
