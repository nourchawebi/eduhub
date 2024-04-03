package com.esprit.cloudcraft.services;

import com.esprit.cloudcraft.entities.Book;
import com.esprit.cloudcraft.entities.Category;
import com.esprit.cloudcraft.entities.User;

import java.util.List;

public interface BookService {
    Boolean addBook (User user, Book book);
    List<Book> getAllBooks();
    Book getBookByID(Long id);
    Book UpdateBook (Book book);
    Book UpdateBookAvailabilityToNOT_AVAILABILE (Book book);
    Book UpdateBookAvailabilityToAVAILABILE (Book book);
    void deleteBook (Book book);
    List<Book> findBookByCategory(Category category);

    List<Book> getBooksByUser(User user);
}
