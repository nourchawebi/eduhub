package com.example.anoncemanag.entities;

import com.example.anoncemanag.enums.TypeAnnonce;
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
@Table(name="annonce")
public class Annonce implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id_annonce;
    private long id_user;
    private String annonce_description;
    private int comment_number;
    private String image;
    private Date annonce_date;
    @Enumerated(EnumType.STRING)
    private TypeAnnonce typeAnnonce;
    private int like_number;





}
