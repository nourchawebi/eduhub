package com.esprit.cloudcraft.upload;


import lombok.Builder;
import lombok.Data;

import java.nio.file.Path;

@Data
@Builder
public class FileObject {
    private String fileName;
    private Path fileLocation;
    private FileType fileType;
}
