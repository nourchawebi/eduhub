package com.example.carpooling.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class User {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userId;
    @OneToMany(cascade = CascadeType.ALL,mappedBy = "carpooled")
    private List<Participation> participations;
    @OneToMany(cascade = CascadeType.ALL,mappedBy = "motorized")
    private List<Journey> journeys;
    @OneToOne
    private Location location;
}
