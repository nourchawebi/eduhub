package com.example.anoncemanag.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
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
    private String comment_description;
    private Date comment_date;

    @ManyToOne
    @JsonIgnore
    Annonce annonce;

    @ManyToOne
    @JsonIgnore
    User user;
}
