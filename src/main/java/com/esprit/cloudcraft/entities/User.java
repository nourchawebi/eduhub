package com.esprit.cloudcraft.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id ;
    private String firstName;
    private String lastName;

    private String email;
    private String password;
    private boolean mfaEnabled;
    private boolean notLocker=true;
    @Temporal(TemporalType.DATE)
    private Date birthDate;
    @Enumerated(EnumType.STRING)
    ClassType classType;
    @Enumerated(EnumType.STRING)
    private RoleType role;
    private boolean enable;
    @OneToMany(cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Book> books;
    @OneToMany(cascade = CascadeType.ALL)
    @JsonIgnore
    private List<BookLoan> bookLoans;

}
