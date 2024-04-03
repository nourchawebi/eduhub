package com.event.test.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.Date;
import java.util.Set;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
@Setter
public class Event  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long idEvent;
    String title;
    LocalDate dateBegin;
    LocalDate dateEnd;
    String location;
    String details ;
    String description;
    int capacity;
    @JsonIgnore
    @ToString.Exclude
    @ManyToOne
    Club club;
    @JsonIgnore
    @ToString.Exclude
    @ManyToMany(cascade = CascadeType.ALL)
    Set<User> userSet;

}
