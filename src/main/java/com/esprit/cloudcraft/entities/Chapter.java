package com.esprit.cloudcraft.entities;

import com.esprit.cloudcraft.entities.userEntities.User;
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
public class Chapter implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long chapterId;

    private String title;
    @Column( length = 100000 )
    private String description;





    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Content> chapterContent;

    @OneToMany( cascade = CascadeType.ALL)
    private List<Summary> summaries;

    @OneToMany( cascade = CascadeType.ALL)
    private List<Rating> ratings;

    @ManyToOne
    private User owner;







}
