package com.esprit.cloudcraft.entities;

import jakarta.persistence.*;
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
public class Summary implements Serializable {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long summaryId;

    private String title;




    @OneToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    private List<FileEntity> files;


    @OneToMany
    private List<Rating> ratings;


    @ManyToOne
    private User owner;
    @Column( length = 100000 )
    private String description;


}
