package com.esprit.cloudcraft.module;


import com.esprit.cloudcraft.upload.FileType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FileEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long fileId;


    private String fileName;

    @Enumerated(EnumType.STRING)
    private FileType fileType;

    private String fileLocation;

    private String url;



}
