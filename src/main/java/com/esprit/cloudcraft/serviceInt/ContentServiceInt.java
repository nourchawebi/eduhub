package com.esprit.cloudcraft.serviceInt;

import com.esprit.cloudcraft.dto.ContentRequest;
import com.esprit.cloudcraft.entities.Content;
import com.esprit.cloudcraft.entities.userEntities.User;

public interface ContentServiceInt {
    public Content findContentById(Long contentId);
    public Content saveContent(ContentRequest contentRequest, User user);

    public boolean deleteContentById(Long contentId);

    public Content updateContent(Long contentId,ContentRequest contentRequest);

}
