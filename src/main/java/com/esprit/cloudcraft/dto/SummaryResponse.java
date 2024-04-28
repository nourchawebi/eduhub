package com.esprit.cloudcraft.dto;


import com.esprit.cloudcraft.entities.Rating;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SummaryResponse {
    private Long id;
    private String title;
    private String description;
    private List<FileEntityResponse> files;
    private List<Rating> ratings;
}
