package com.esprit.cloudcraft.dto;


import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@Builder
public class ChapterRequest {

    private String title;
    private String description;
}
