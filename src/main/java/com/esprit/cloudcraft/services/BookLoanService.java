package com.esprit.cloudcraft.services;

import com.esprit.cloudcraft.entities.Book;
import com.esprit.cloudcraft.entities.BookLoan;
import com.esprit.cloudcraft.entities.userEntities.User;


import java.util.List;

public interface BookLoanService {
     //BookLoan addBookLoan (Book book, User user);
     Boolean addBookLoan(User user, Book book);
     List<BookLoan> getAllBookLoans();
     BookLoan getBookLoanById(Long id);
     BookLoan UpdateBookLoan (BookLoan bookLoan);
     void deleteBookLoan (BookLoan bookLoan);
     List<BookLoan> getBookLoansByUser(User user);


}
