package com.esprit.cloudcraft.services;

import com.esprit.cloudcraft.dto.BookRequest;
import com.esprit.cloudcraft.entities.Book;

public interface BookMapperService {
    Book toBook(BookRequest request);
}
