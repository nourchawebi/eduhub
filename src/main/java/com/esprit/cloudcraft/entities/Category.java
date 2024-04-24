package com.esprit.cloudcraft.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Category implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idCategory ;
    private String name ;
    private String description ;
    @OneToMany(mappedBy = "category",fetch = FetchType.LAZY)
    private List<Book> books ;
}

