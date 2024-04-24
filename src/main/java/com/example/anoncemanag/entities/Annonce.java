package com.example.anoncemanag.entities;

import com.example.anoncemanag.enums.TypeAnnonce;
import com.example.anoncemanag.enums.TypeInternship;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name="annonce")
public class Annonce implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id_annonce;
    private String title;
    private String annonce_description;
    @Column(nullable = true)
    private String image;
    private LocalDate annonce_date;
    @DateTimeFormat
    private LocalDate startDate;
    @Enumerated(EnumType.STRING)
    private TypeAnnonce typeAnnonce;
    @Enumerated(EnumType.STRING)
    private TypeInternship typeInternship;
    private String location;
    private String locationLx;
    private String  locationLy;
    private String governorate;
    private String country;

    @ManyToOne
    @JsonIgnore
    User user;

    @OneToMany(mappedBy = "annonce", cascade = CascadeType.ALL, orphanRemoval = true)

    private List<Comment> comments = new ArrayList<>();

    @OneToMany
    @JsonIgnore
    List <React>reacts=new ArrayList<>();







}
