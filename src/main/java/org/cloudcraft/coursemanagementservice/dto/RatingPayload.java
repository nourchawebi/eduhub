package org.cloudcraft.coursemanagementservice.dto;


import lombok.Builder;
import lombok.Data;
import org.cloudcraft.coursemanagementservice.module.Rating;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Builder
public class RatingPayload {
    private Long id;
    private String content;
    private String title;
    private Integer value;
    private Date createdAt;
}
