package com.library;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Entry point for the Spring Boot Library Management application.
 *
 * @SpringBootApplication combines:
 *   - @Configuration       (marks this as a config class)
 *   - @EnableAutoConfiguration (lets Spring Boot configure beans automatically)
 *   - @ComponentScan       (scans com.library and sub-packages)
 */
@SpringBootApplication
public class LibraryManagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(LibraryManagementApplication.class, args);
    }
}
