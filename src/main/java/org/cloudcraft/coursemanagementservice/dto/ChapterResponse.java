package org.cloudcraft.coursemanagementservice.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ChapterResponse {
    private Long id;
    private String title;
    private String description;
    private List<ContentResponse> content;
    private List<RatingPayload> ratings;
}
