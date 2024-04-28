package com.esprit.cloudcraft.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.util.Date;

@Data
@Entity
public class Location {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer locationId;
    private Double latitude;
    private Double longitude;
    private String nameLocation;
}
