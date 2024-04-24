package com.esprit.cloudcraft.implement;

import com.esprit.cloudcraft.dto.BookRequest;
import com.esprit.cloudcraft.dto.BookResponse;
import com.esprit.cloudcraft.entities.AvailabilityType;
import com.esprit.cloudcraft.entities.Book;
import com.esprit.cloudcraft.services.BookMapperService;
import com.esprit.cloudcraft.services.FileStorageService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class BookMapperServiceImp implements BookMapperService {
    @Resource
    private FileStorageService fileStorageService;
    @Override
    public Book toBook(String tit, String desc, String auth ) {
        return Book.builder()
                .title(tit)
                .description(desc)
                .author(auth)
                .availability(AvailabilityType.AVAILABILE)
                .publicationDate(new Date())
                .build();
    }

    @Override
    public BookResponse toBookResponse(Book book) {
        return BookResponse.builder()
                .id(book.getIdBook())
                .title(book.getTitle())
                .description(book.getDescription())
                .author(book.getAuthor())
                .category(book.getCategory().getName())
                .coverPicture(fileStorageService.getPicture(book.getPicture()))
                .availability(book.getAvailability().toString())
                .build() ;
    }
}
