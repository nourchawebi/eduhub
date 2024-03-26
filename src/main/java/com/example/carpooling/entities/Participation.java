package com.example.carpooling.entities;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Participation {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer participationId;
    @ManyToOne
    private User carpooled;
    @OneToOne(cascade = CascadeType.ALL)
    private Journey journey;
}
