package com.example.carpooling.entities;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Date;

@Data
@Entity
public class Car {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer carId;
    private String manufacturer;
    private String model;
    private File image;
    @Temporal(TemporalType.DATE)
    private Date year;
    private Integer capacity;
}
