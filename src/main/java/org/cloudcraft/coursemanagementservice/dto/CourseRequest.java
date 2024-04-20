package org.cloudcraft.coursemanagementservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.cloudcraft.coursemanagementservice.module.CourseCategory;
import org.cloudcraft.coursemanagementservice.module.UneversityYear;
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
