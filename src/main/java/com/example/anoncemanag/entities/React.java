package com.example.anoncemanag.entities;

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
    private long id_user;

    @JsonIgnore
    @ManyToOne (cascade =CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinColumn(name = "id_annonce")
    private Annonce annonce;
}
