package com.esprit.cloudcraft.entities;

import com.esprit.cloudcraft.entities.userEntities.User;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@Entity
public class Journey {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer journeyId;
    private Double price;
    @Enumerated(EnumType.ORDINAL)
    private Day day;
    @JsonFormat(pattern="HH:mm")
    private Date leavingTime;
    //@Temporal(TemporalType.TIME)
    @JsonFormat(pattern="HH:mm")
    private Date returnTime;
    private Integer availablePlaces;

    @OneToMany(cascade=CascadeType.ALL,fetch = FetchType.EAGER)
    private List<Location> traject;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Participation> participations;

    @JsonIgnore
    @ManyToOne
    private User motorized;

}
