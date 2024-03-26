package com.example.carpooling.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity
public class Car {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer carId;
    private String manufacturer;
    private String model;
    @Temporal(TemporalType.DATE)
    private Date year;
    private Integer capacity;

    @ManyToOne User motorized;
}
