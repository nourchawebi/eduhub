package com.esprit.cloudcraft.dto;

import com.esprit.cloudcraft.entities.Book;
import com.esprit.cloudcraft.entities.Category;
import com.esprit.cloudcraft.entities.User;
import lombok.Data;

@Data
public class BookDto {
    private User user;
    private Book book;

}
