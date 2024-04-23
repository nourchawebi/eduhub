package com.esprit.cloudcraft.dto;

import com.esprit.cloudcraft.entities.Book;
import com.esprit.cloudcraft.entities.Category;
import com.esprit.cloudcraft.entities.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookCategoryDto {
    private User user;
    private Book book;
    private Category category;
    private MultipartFile file;
}
