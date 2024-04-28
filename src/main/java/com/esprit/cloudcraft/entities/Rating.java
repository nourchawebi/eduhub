package com.esprit.cloudcraft.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;


@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Rating implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ratingId;

    private String title;
    @Column( length = 100000 )
    private String content;

    private Integer value;

    @Temporal(TemporalType.DATE)
    private Date createdAt ;

    @Temporal(TemporalType.DATE)
    private Date modifiedAt ;


    @ManyToOne
    private User owner;



}
