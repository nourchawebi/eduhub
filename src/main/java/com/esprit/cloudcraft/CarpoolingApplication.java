package com.esprit.cloudcraft;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class CarpoolingApplication {

    public static void main(String[] args) {
        SpringApplication.run(CarpoolingApplication.class, args);
    }

}
