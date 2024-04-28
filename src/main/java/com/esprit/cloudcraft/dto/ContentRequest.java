package com.esprit.cloudcraft.dto;


import lombok.Data;
import com.esprit.cloudcraft.entities.ContentCategory;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class ContentRequest {
    private String contentTitle;
    private String contentDescription;
    private ContentCategory contentCategory;
    private List<MultipartFile> files;
}
