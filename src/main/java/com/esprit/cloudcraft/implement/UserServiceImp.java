package com.esprit.cloudcraft.implement;

import com.esprit.cloudcraft.entities.Book;
import com.esprit.cloudcraft.entities.BookLoan;
import com.esprit.cloudcraft.entities.User;
import com.esprit.cloudcraft.repository.UserDao;
import com.esprit.cloudcraft.services.UserService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class UserServiceImp implements UserService {
    @Resource
    private UserDao userDao;

    @Override
    public User findUserById(Long id) {
        if(id!= null)
        {
            final Optional<User> optionalUser= userDao.findById(id);
            if (optionalUser.isPresent())
            {
                return optionalUser.get();
            }
        }
        return null;
    }

    @Override
    public User UpdateUser(User user) {
        return userDao.saveAndFlush(user);
    }


///Partie Gestion bibliothèque
    @Override
    public List<Book> getBooksByUser(User user) {
        User requestedUser = this.findUserById(user.getId());
        if (user != null) {
            return requestedUser.getBooks();
        }
        return null;
    }

    @Override
    public List<BookLoan> getBookLoansByUser(User user) {
        User requestedUser = this.findUserById(user.getId());
        if (user != null) {
            return requestedUser.getBookLoans();
        }
        return null;
    }
    @Override
    public void addBookLoanToUser(User user, BookLoan bookLoan) {
        user.getBookLoans().add(bookLoan);
        userDao.save(user);
    }

    @Override
    public void addBookToUser(User user, Book book) {
        if (user != null && book != null) {
            user.getBooks().add(book);
            userDao.save(user);
        }
    }
}

