package com.esprit.cloudcraft.implement;

import com.esprit.cloudcraft.entities.*;
import com.esprit.cloudcraft.repository.BookDao;
import com.esprit.cloudcraft.repository.CategoryDao;
import com.esprit.cloudcraft.services.BookService;
import com.esprit.cloudcraft.services.UserService;
import jakarta.annotation.Resource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImp implements BookService {
    @Resource
    private BookDao bookDao;

    @Resource
    private UserService userService;

    @Override
    public Boolean addBook(User user,Book book) {
        boolean result = Boolean.FALSE;
        final User requestedUser = userService.findUserById(user.getId());
        if (user!= null && book!= null){
            userService.addBookToUser(requestedUser, book);
            bookDao.save(book);
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
