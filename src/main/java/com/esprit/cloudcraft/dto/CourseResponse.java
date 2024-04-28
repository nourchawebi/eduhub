package com.esprit.cloudcraft.dto;


import lombok.Builder;
import lombok.Data;
import com.esprit.cloudcraft.module.CourseCategory;

@Data
@Builder
public class CourseResponse {
    private Long id;
    private String name;
    private String description ;
    private FileEntityResponse image;
    private CourseCategory courseCategory;



}
