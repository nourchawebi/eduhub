package com.esprit.cloudcraft.services;

import com.esprit.cloudcraft.dto.BookBorrowResponse;
import com.esprit.cloudcraft.dto.BookResponse;
import com.esprit.cloudcraft.dto.PageResponse;
import com.esprit.cloudcraft.entities.Book;
import com.esprit.cloudcraft.entities.BookLoan;
import com.esprit.cloudcraft.entities.userEntities.User;

import java.util.List;

public interface BookLoanService {
     Boolean addBookLoan(User user, Book book);
     List<BookLoan> getAllBookLoans();
     BookLoan getBookLoanById(Long id);
     PageResponse<BookBorrowResponse> findBookLoansByUser(int page, int size, User user);

     Boolean returnBookLoan(Long idBook);
}