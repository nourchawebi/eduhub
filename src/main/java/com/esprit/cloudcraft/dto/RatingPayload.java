package com.esprit.cloudcraft.dto;


import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class RatingPayload {
    private Long id;
    private String content;
    private String title;
    private Integer value;
    private Date createdAt;
    private UserResponse owner;
}
