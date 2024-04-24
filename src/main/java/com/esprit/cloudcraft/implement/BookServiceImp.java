package com.esprit.cloudcraft.implement;

import com.esprit.cloudcraft.dto.BookResponse;
import com.esprit.cloudcraft.dto.PageResponse;
import com.esprit.cloudcraft.entities.*;
import com.esprit.cloudcraft.repository.BookDao;
import com.esprit.cloudcraft.services.*;
import jakarta.annotation.Resource;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImp implements BookService {
    @Resource
    private BookDao bookDao;

    @Resource
    private UserService userService;
    @Resource
    private FileStorageService fileStorageService ;

    @Resource
    private CategoryService categoryService ;
    @Resource
    private BookMapperService bookMapperService;


    @Override
    public Boolean addBook (Book newbook , Long iduser, Long idcategory,MultipartFile image) {
        boolean result = Boolean.FALSE;
        final User requestedUser = userService.findUserById(iduser);
         Category requesteCategory = categoryService.getCategoryByID(idcategory);
        Book book =new Book();
        if (requestedUser!= null && newbook!= null && requesteCategory!=null){

            book.setCategory(requesteCategory);
            book.setAuthor(newbook.getAuthor());
            book.setDescription(newbook.getDescription());
            book.setTitle(newbook.getTitle());
            book.setPicture(fileStorageService.saveImage(image));
            book.setAvailability(AvailabilityType.AVAILABILE);
            book.setPublicationDate(new Date());
            Book savedbook=bookDao.save(book);

            requesteCategory.getBooks().add(savedbook);
            categoryService.UpdateCategory(requesteCategory);

            userService.addBookToUser(requestedUser, savedbook);

            result = Boolean.TRUE;
        }

        return result;
    }

    @Override
    public List<Book> getAllBooks() {
        return bookDao.findAll();
    }

    @Override
    public Book getBookByID(Long id) {

        if(id!= null)
        {
            final Optional<Book> optionalBook= bookDao.findById(id);
            if (optionalBook.isPresent())
            {
                return optionalBook.get();
            }
        }
        return null;
    }

    @Override
    public Book UpdateBook(Book book) {
        return bookDao.saveAndFlush(book);
    }



    @Override
    public void deleteBook(Book book) {
        bookDao.delete(book);

    }

    @Override
    public List<Book> findBookByCategory(Category category) {
        if (!bookDao.findBookByCategory(category).isEmpty())
        {
            return bookDao.findBookByCategory(category);
        }
        else
        {
            return null;
        }
    }

    @Override
    public List<Book> getBooksByUser(User user) {
        final User requestedUser = userService.findUserById(user.getId());
        if (requestedUser!= null)
        {
            return requestedUser.getBooks();
        }
        return null;
    }

    @Override
    public BookResponse findById(Long idBook) {
        return bookDao.findById(idBook)
                .map(bookMapperService::toBookResponse)
                .orElseThrow(() -> new EntityNotFoundException("No book found with id "+ idBook));
    }

    @Override
    public PageResponse<BookResponse> findAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Book> books = bookDao.findAll(pageable);
        List<BookResponse>bookResponses = books.stream()
                .map(bookMapperService::toBookResponse)
                .toList();
        return new PageResponse<>(
                bookResponses,
                books.getNumber(),
                books.getSize(),
                books.getTotalElements(),
                books.getTotalPages(),
                books.isFirst(),
                books.isLast()
        );
    }


}
