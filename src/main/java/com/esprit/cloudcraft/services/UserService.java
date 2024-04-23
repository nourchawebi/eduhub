package com.esprit.cloudcraft.services;

import com.esprit.cloudcraft.entities.Book;
import com.esprit.cloudcraft.entities.BookLoan;
import com.esprit.cloudcraft.entities.User;

import java.util.List;

public interface UserService {
     User findUserById(Long id);

     List<Book> getBooksByUser(User user);
     List<BookLoan> getBookLoansByUser(User user);

     User UpdateUser(User user);

     void addBookLoanToUser(User user, BookLoan bookLoan);

     void addBookToUser(User user, Book book);

}
