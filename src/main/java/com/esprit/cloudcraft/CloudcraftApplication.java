package com.esprit.cloudcraft;


import com.esprit.cloudcraft.upload.IFileUploadService;
import jakarta.annotation.Resource;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;



import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;


@EnableJpaAuditing
@SpringBootApplication
//@EnableScheduling

public class CloudcraftApplication {

	public static void main(String[] args) {
		SpringApplication.run(CloudcraftApplication.class, args);
	}

}


