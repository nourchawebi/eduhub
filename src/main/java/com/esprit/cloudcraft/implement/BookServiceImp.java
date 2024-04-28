package com.esprit.cloudcraft.implement;

import com.esprit.cloudcraft.dto.BookRequest;
import com.esprit.cloudcraft.entities.*;
import com.esprit.cloudcraft.repository.BookDao;
import com.esprit.cloudcraft.repository.CategoryDao;
import com.esprit.cloudcraft.services.*;
import com.esprit.cloudcraft.services.userServices.UserService;
import jakarta.annotation.Resource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.esprit.cloudcraft.entities.userEntities.User;
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
    public Book UpdateBookAvailabilityToNOT_AVAILABILE(Book book) {
        book.setAvailability(AvailabilityType.NOT_AVAILABILE);
        return bookDao.saveAndFlush(book);
    }

    @Override
    public Book UpdateBookAvailabilityToAVAILABILE(Book book) {
        book.setAvailability(AvailabilityType.AVAILABILE);
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




}
