package com.esprit.cloudcraft.implement;

import com.esprit.cloudcraft.dto.BookRequest;
import com.esprit.cloudcraft.entities.Book;
import com.esprit.cloudcraft.services.BookMapperService;
import org.springframework.stereotype.Service;

@Service
public class BookMapperServiceImp implements BookMapperService {

    @Override
    public Book toBook(BookRequest request) {
        return Book.builder()
                .idBook(request.id())
                .title(request.title())
                .description(request.description())
                .author(request.author())
                .build();
    }

}
