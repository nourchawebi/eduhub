package com.esprit.cloudcraft.dto;


import com.esprit.cloudcraft.module.CourseCategory;
import com.esprit.cloudcraft.module.UneversityYear;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CourseDetailedResponse {
    private Long id;
    private String name;
    private String description ;
    private FileEntityResponse image;
    private CourseCategory courseCategory;
    private List<RatingPayload> ratings;
    private List<ChapterResponse> chapters;
    private List<SummaryResponse> summaries;
    private UserResponse owner;
    private UneversityYear uneversityYear;

}
