package com.esprit.cloudcraft.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;


@Data
@Builder
public class ChapterDetailedResponse {
    private Long id;
    private String title;
    private String description;
    private List<ContentResponse> content;
    private List<SummaryResponse> summaries;
    private List<RatingPayload> ratings;
    private UserResponse owner;

}
