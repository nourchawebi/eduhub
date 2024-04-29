package com.esprit.cloudcraft.implement;

import com.esprit.cloudcraft.dto.BookResponse;
import com.esprit.cloudcraft.entities.AvailabilityType;
import com.esprit.cloudcraft.entities.Book;
import com.esprit.cloudcraft.services.MapperService;
import com.esprit.cloudcraft.services.FileStorageService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class MapperServiceImp implements MapperService {
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
        String imageUrl="http://localhost:8080/book/"+ book.getPicture();
        return BookResponse.builder()
                .id(book.getIdBook())
                .title(book.getTitle())
                .description(book.getDescription())
                .author(book.getAuthor())
                .owner(book.getUser().getFirstName()+" "+book.getUser().getLastName())
                .category(book.getCategory().getName())
                .coverPicture(imageUrl)
                .availability(book.getAvailability().toString())
                .build() ;
    }




}
