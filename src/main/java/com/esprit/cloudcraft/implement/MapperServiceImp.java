package com.esprit.cloudcraft.implement;

import com.esprit.cloudcraft.dto.BookBorrowResponse;
import com.esprit.cloudcraft.dto.BookResponse;
import com.esprit.cloudcraft.entities.AvailabilityType;
import com.esprit.cloudcraft.entities.Book;
import com.esprit.cloudcraft.entities.BookLoan;
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

    @Override
    public BookBorrowResponse toBookBorrowResponse(BookLoan bookLoan) {
        String imageUrl="http://localhost:8080/book/"+ bookLoan.getBook().getPicture();
        return BookBorrowResponse.builder()
                .id(bookLoan.getIdBookLoan())
                .loanDate(bookLoan.getLoanDate())
                .dueDate(bookLoan.getDueDate())
                .idBook(bookLoan.getBook().getIdBook())
                .title(bookLoan.getBook().getTitle())
                .author(bookLoan.getBook().getAuthor())
                .returned(bookLoan.isReturned())
                .owner(bookLoan.getUser().getFirstName()+" "+bookLoan.getUser().getLastName())
                .category(bookLoan.getBook().getCategory().getName())
                .coverPicture(imageUrl)
                .description(bookLoan.getBook().getDescription())
                .build();
    }


}
