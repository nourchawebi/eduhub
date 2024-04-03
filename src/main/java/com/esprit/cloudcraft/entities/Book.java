package com.esprit.cloudcraft.entities;

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
public class Book implements Serializable {
    @Id@GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idBook ;
    private String title;
    private String picture;
    private String author ;
    private String description ;
    @Enumerated(EnumType.STRING)
    private AvailabilityType availability ;
    @Temporal(TemporalType.DATE)
    private Date publicationDate ;
    @ManyToOne
    private  Category category ;
    @OneToMany(mappedBy = "book", fetch = FetchType.EAGER)
    private List<BookLoan> bookLoans;

}
