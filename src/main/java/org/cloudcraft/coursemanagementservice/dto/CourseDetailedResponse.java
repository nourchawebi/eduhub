package org.cloudcraft.coursemanagementservice.dto;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.cloudcraft.coursemanagementservice.module.*;

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
