package com.brakkits;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

/**
 * Ali Cooper
 * brakkits
 * CST-452
 * 10/18/2020
 *
 * Entry Point for main application
 */
@EnableWebSecurity
@SpringBootApplication
public class BrakkitsApplication {

    public static void main(String[] args) {
        SpringApplication.run(BrakkitsApplication.class, args);
    }

}
