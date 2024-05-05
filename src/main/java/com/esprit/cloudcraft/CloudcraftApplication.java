package com.esprit.cloudcraft;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


import org.springframework.data.jpa.repository.config.EnableJpaAuditing;


@EnableJpaAuditing
@SpringBootApplication
//@EnableScheduling

public class CloudcraftApplication {

    public static void main(String[] args) {
        SpringApplication.run(CloudcraftApplication.class, args);
    }

}