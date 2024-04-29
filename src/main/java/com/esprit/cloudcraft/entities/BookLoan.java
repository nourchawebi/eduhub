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
@Table(name = "BookLoan")
public class BookLoan implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idBookLoan ;
    @Temporal(TemporalType.DATE)
    private Date loanDate ;
    @Temporal(TemporalType.DATE)
    private Date dueDate ;
    private boolean returned;
    @ManyToOne
    private Book book;
    @ManyToOne
    private User user;
}

