package com.esprit.cloudcraft.dto;

import lombok.*;

@Builder

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BookResponse {
    private Long id ;
    private String title;
    private String description;
    private String author ;
    private String owner;
    private String category ;
    private String coverPicture ;
    private String availability;

}