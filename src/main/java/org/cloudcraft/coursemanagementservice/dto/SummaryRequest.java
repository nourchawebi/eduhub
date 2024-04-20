package org.cloudcraft.coursemanagementservice.dto;

import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.cloudcraft.coursemanagementservice.module.Content;
import org.cloudcraft.coursemanagementservice.module.Rating;
import org.cloudcraft.coursemanagementservice.module.User;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SummaryRequest {
    private String title;
    private List<MultipartFile> files;
    private String description;
}
