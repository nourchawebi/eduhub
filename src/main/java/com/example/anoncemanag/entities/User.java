package com.example.anoncemanag.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Entity
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public class User implements Serializable {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long  id ;
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

        @OneToMany(mappedBy = "user")
    List<Comment> comments=new ArrayList<>();

@OneToMany(mappedBy = "user")
    List <React>reacts=new ArrayList<>();

}
