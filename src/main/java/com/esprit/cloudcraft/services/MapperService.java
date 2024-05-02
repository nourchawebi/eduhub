package com.esprit.cloudcraft.services;

import com.esprit.cloudcraft.dto.BookBorrowResponse;
import com.esprit.cloudcraft.dto.BookResponse;
import com.esprit.cloudcraft.entities.Book;
import com.esprit.cloudcraft.entities.BookLoan;

public interface MapperService {


    BookResponse toBookResponse(Book book);
    BookBorrowResponse toBookBorrowResponse(BookLoan bookLoan);

}
