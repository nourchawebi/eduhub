package org.cloudcraft.coursemanagementservice.serviceInt;

import org.cloudcraft.coursemanagementservice.dto.ContentRequest;
import org.cloudcraft.coursemanagementservice.dto.ContentResponse;
import org.cloudcraft.coursemanagementservice.module.Content;
import org.springframework.web.multipart.MultipartFile;

public interface ContentServiceInt {
    public Content findContentById(Long contentId);
    public Content saveContent(ContentRequest contentRequest);

    public boolean deleteContentById(Long contentId);

    public Content updateContent(Long contentId,ContentRequest contentRequest);

}
