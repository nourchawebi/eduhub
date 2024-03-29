package com.example.carpooling.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Participation {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer participationId;
    @JsonIgnore
    @ManyToOne
    private User carpooled;
    @JsonIgnore
    @OneToOne(cascade = CascadeType.ALL)
    private Journey journey;
}
