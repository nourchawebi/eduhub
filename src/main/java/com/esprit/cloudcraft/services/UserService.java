package com.esprit.cloudcraft.services;

import com.esprit.cloudcraft.entities.Book;
import com.esprit.cloudcraft.entities.BookLoan;
import com.esprit.cloudcraft.entities.User;

import java.util.List;

public interface UserService {
     User findUserById(Long id);

     User UpdateUser(User user);
     void addBookLoanToUser(User user, BookLoan bookLoan);



}
