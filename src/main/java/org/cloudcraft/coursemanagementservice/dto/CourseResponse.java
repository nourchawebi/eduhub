package org.cloudcraft.coursemanagementservice.dto;


import lombok.Builder;
import lombok.Data;
import org.cloudcraft.coursemanagementservice.module.CourseCategory;

@Data
@Builder
public class CourseResponse {
    private Long id;
    private String name;
    private String description ;
    private FileEntityResponse image;
    private CourseCategory courseCategory;



}
