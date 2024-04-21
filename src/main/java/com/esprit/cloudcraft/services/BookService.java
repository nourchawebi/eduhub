package com.esprit.cloudcraft.services;

import com.esprit.cloudcraft.dto.BookRequest;
import com.esprit.cloudcraft.entities.Book;
import com.esprit.cloudcraft.entities.Category;
import com.esprit.cloudcraft.entities.User;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface BookService {
    public Boolean addBook (Book newbook , Long iduser, Long idcategory,MultipartFile image);
    List<Book> getAllBooks();
    Book getBookByID(Long id);
    Book UpdateBook (Book book);
    Book UpdateBookAvailabilityToNOT_AVAILABILE (Book book);
    Book UpdateBookAvailabilityToAVAILABILE (Book book);
    void deleteBook (Book book);
    List<Book> findBookByCategory(Category category);

    List<Book> getBooksByUser(User user);
    public void uploadBookCoverPicture(MultipartFile file, User connectedUser, Long bookId);
}
