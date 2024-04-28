package com.esprit.cloudcraft.upload;


import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

@RestController
@RequestMapping("/files")
public class FileController {
    @Autowired
    FileUploadService fileService;

    @GetMapping("/{fileName}")
    public ResponseEntity<Resource> downloadPdf(@PathVariable String fileName, @PathParam("fileType") FileType fileType) throws IOException
    {
        // Construct the file path
        Path filePath =fileService.getFilePath(fileName,fileType);
        Resource resource = new UrlResource(filePath.toUri());

        // Check if the file exists
        if (!resource.exists()) {
            return ResponseEntity.notFound().build();
        }

        // Set content type as application/pdf
        HttpHeaders headers = new HttpHeaders();
        String contentType;
        if (fileName.toLowerCase().endsWith(".pdf")) {
            contentType = MediaType.APPLICATION_PDF_VALUE;
        } else if (fileName.toLowerCase().endsWith(".png") || fileName.toLowerCase().endsWith(".jpg") || fileName.toLowerCase().endsWith(".jpeg")) {
            contentType = MediaType.IMAGE_JPEG_VALUE; // You can use IMAGE_PNG_VALUE for PNG files
        } else {
            // Default to binary data if the file type is unknown
            contentType = MediaType.APPLICATION_OCTET_STREAM_VALUE;
        }
        headers.add(HttpHeaders.CONTENT_TYPE, contentType);

        // Return the file as ResponseEntity with appropriate headers
        return ResponseEntity.ok()
                .headers(headers)
                .body(resource);
    }

    @DeleteMapping("/{filename}")
    public ResponseEntity<Boolean> deleteFileByName(String fileName,@PathParam("fileType") FileType fileType){
        try {

            String fileLocation="";
            if(fileType==FileType.PDF) fileLocation="images";
            if(fileType==FileType.IMAGE) fileLocation="pdfs";
            File file = ResourceUtils.getFile("classpath:uploads/" +fileLocation+"/"+ fileName);

            if (file.exists()) {
                return ResponseEntity.ok().body(file.delete());
            } else {
                System.out.println("File not found: " + fileName);
                return ResponseEntity.ok().body(false);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok().body(false);
    }
}
