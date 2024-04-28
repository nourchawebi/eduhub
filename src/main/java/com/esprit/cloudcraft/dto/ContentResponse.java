package com.esprit.cloudcraft.dto;


import lombok.Builder;
import lombok.Data;
import com.esprit.cloudcraft.entities.ContentCategory;

import java.util.List;

@Data
@Builder
public class ContentResponse {
    private Long contentId;
    private String contentDescription;
    private String contentTitle;
    private ContentCategory contentCategory;
    List<FileEntityResponse> files;
}
