package com.esprit.cloudcraft.entities;

import com.esprit.cloudcraft.entities.userEntities.User;
import com.fasterxml.jackson.annotation.JsonFormat;
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
    private String registrationNumber;
    @Temporal(TemporalType.DATE)
    @JsonFormat(pattern="yyyy-MM")
    private Date year;
    private Integer capacity;
}
