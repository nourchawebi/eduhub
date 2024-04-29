package com.esprit.cloudcraft.services;

import com.esprit.cloudcraft.dto.BookResponse;
import com.esprit.cloudcraft.entities.Book;

public interface MapperService {

    Book toBook(String title, String description, String author );
    BookResponse toBookResponse(Book book);

}
