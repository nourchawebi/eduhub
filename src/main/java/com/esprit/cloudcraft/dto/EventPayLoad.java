package com.esprit.cloudcraft.dto;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Setter
@Builder
public class EventPayLoad {

    Long idEvent;
    String title;
    LocalDate dateBegin;
    LocalDate dateEnd;
    String location;
    String details ;
    String description;
    int capacity;
    String imageURL;
}
