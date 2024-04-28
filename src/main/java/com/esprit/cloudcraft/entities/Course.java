package com.esprit.cloudcraft.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;


@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Course implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long courseId;

    @NotNull(message = "Course name cannot be null")
    @NotNull
    @NotBlank(message = "Course name cannot be a blank ('')")
    private String name;
    @Column( length = 100000 )
    private String description;





    @Enumerated(EnumType.STRING)
    private UneversityYear uneversityYear;

    @Enumerated(EnumType.STRING)
    private CourseCategory courseCategory;


    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private FileEntity image;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Rating> rating;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Chapter> chapters;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Summary> summaries;


    @ManyToOne
    private User owner;



}
