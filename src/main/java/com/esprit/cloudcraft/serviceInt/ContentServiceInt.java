package com.esprit.cloudcraft.serviceInt;

import com.esprit.cloudcraft.dto.ContentRequest;
import com.esprit.cloudcraft.module.Content;

public interface ContentServiceInt {
    public Content findContentById(Long contentId);
    public Content saveContent(ContentRequest contentRequest);

    public boolean deleteContentById(Long contentId);

    public Content updateContent(Long contentId,ContentRequest contentRequest);

}
