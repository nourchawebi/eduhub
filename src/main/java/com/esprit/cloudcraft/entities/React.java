package com.esprit.cloudcraft.entities;

import com.esprit.cloudcraft.Enum.TypeReact;
import com.esprit.cloudcraft.entities.userEntities.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name="react")
public class React implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id_react;
    @Enumerated(EnumType.STRING)
    TypeReact typeReact;
    private int likes;
    private int dislikes;



    @ManyToOne
    @JsonIgnore
    private Annonce annonce;

    @ManyToOne
    @JsonIgnore
    private User user;
}
