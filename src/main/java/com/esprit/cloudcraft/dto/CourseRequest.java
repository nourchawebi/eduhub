package com.esprit.cloudcraft.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.esprit.cloudcraft.entities.CourseCategory;
import com.esprit.cloudcraft.entities.UneversityYear;
import org.springframework.web.multipart.MultipartFile;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CourseRequest {
    private Long courseId;
    private String name;
    private MultipartFile image;
    private String description;
    private UneversityYear uneversityYear;
    private CourseCategory courseCategory;

}
