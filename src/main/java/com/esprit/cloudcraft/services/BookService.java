package com.esprit.cloudcraft.services;
import com.esprit.cloudcraft.dto.BookResponse;
import com.esprit.cloudcraft.dto.PageResponse;
import com.esprit.cloudcraft.entities.Book;
import com.esprit.cloudcraft.entities.userEntities.User;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface BookService {
    public Boolean addBook (Book newbook , Long iduser, Long idcategory,MultipartFile image);
    List<Book> getAllBooks();
    Book getBookByID(Long id);
    Book UpdateBook (Book book);
    void deleteBook (Book book);
    Book saveBook (Long idBook, String title, String author, String description);

    BookResponse findById (Long idBook );

    PageResponse<BookResponse> findAll(int page, int size, Long idUser);

    PageResponse<BookResponse> findBookByUser(int page, int size, User user);

}