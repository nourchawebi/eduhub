package org.cloudcraft.coursemanagementservice;

import jakarta.annotation.Resource;
import org.cloudcraft.coursemanagementservice.upload.IFileUploadService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CourseManagementServiceApplication implements CommandLineRunner {

    @Resource
    private IFileUploadService iFileUploadService;
    public static void main(String[] args) {
        SpringApplication.run(CourseManagementServiceApplication.class, args);
    }


    @Override
    public void run(String... args) throws Exception{
        iFileUploadService.init();
    }



}
