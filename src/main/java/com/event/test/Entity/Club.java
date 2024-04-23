package com.event.test.Entity;

import com.event.test.Enum.Name;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
@Setter
public class Club {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long idClub;
    @Enumerated(EnumType.STRING)
    Name name;
    String description;
    @JsonIgnore
    @ToString.Exclude
    @OneToMany(mappedBy ="club" ,cascade = CascadeType.ALL)
    Set<Event> event;
}
