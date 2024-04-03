package com.example.anoncemanag.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name="comment")
public class Comment implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id_comment;
    private long id_user;
    private String comment_description;
    private Date comment_date;
    @JsonIgnore
    @ManyToOne (cascade =CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinColumn(name = "id_annonce")
    private Annonce annonce;
}
