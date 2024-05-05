package com.esprit.cloudcraft.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class FileEntityResponse {
    private Long fileId;


    private String fileName;

    private String fileLocation;

    private String url;
}
