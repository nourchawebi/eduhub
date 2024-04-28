package com.esprit.cloudcraft;

import com.esprit.cloudcraft.upload.IFileUploadService;
import jakarta.annotation.Resource;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CloudcraftApplication implements CommandLineRunner {

    @Resource
    private IFileUploadService iFileUploadService;
    public static void main(String[] args) {
        SpringApplication.run(CloudcraftApplication.class, args);
    }


    @Override
    public void run(String... args) throws Exception{
        iFileUploadService.init();
    }



}
