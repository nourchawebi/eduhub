package com.esprit.cloudcraft.dto;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
public class BookResponse {
    private Long id ;
    private String title;
    private String description;
    private String author ;
    private String category ;
    private String coverPicture ;
    private String availability;

}
