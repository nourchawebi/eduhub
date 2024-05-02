package com.esprit.cloudcraft.entities;

import com.esprit.cloudcraft.entities.userEntities.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data
@Builder
public class Book implements Serializable {
    @Id@GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idBook ;
    private String title;
    private String picture;
    private String author ;
    @Column(length = 10000)
    private String description ;
    @Enumerated(EnumType.STRING)
    private AvailabilityType availability ;
    @Temporal(TemporalType.DATE)
    private Date publicationDate ;
    @ManyToOne
    private  Category category ;
    @ManyToOne
    private User user;

}
