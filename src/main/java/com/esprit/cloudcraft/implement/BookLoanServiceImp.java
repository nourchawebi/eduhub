package com.esprit.cloudcraft.implement;

import com.esprit.cloudcraft.entities.AvailabilityType;
import com.esprit.cloudcraft.entities.Book;
import com.esprit.cloudcraft.entities.BookLoan;
import com.esprit.cloudcraft.entities.userEntities.User;
import com.esprit.cloudcraft.repository.BookLoanDao;
import com.esprit.cloudcraft.services.BookLoanService;
import com.esprit.cloudcraft.services.BookService;

import com.esprit.cloudcraft.services.userServices.UserService;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class BookLoanServiceImp implements BookLoanService {
    @Resource
    private BookLoanDao bookLoanDao ;

    @Resource
    private UserService userService ;

    @Resource
    private BookService bookService ;

    @Override
    public Boolean addBookLoan(User user, Book book) {
        Boolean result = Boolean.FALSE;
        /* User requestedUser = userService.findUserById(user.getId());
         Book requestedBook = bookService.getBookByID(book.getIdBook());


        if (requestedUser != null && requestedBook != null && requestedBook.getAvailability().equals(AvailabilityType.AVAILABILE)) {
            BookLoan bookLoan = new BookLoan();
            bookLoan.setBook(requestedBook);
            bookLoan.setLoanDate(new Date());
            // Calcul de la dueDate un mois après la loanDate
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(bookLoan.getLoanDate());
            calendar.add(Calendar.MONTH, 1);
            Date dueDate = calendar.getTime();
            bookLoan.setDueDate(dueDate);
            // Mettre à jour l'attribut availability du livre en NOT_AVAILABLE
            requestedBook.setAvailability(AvailabilityType.NOT_AVAILABILE);

            bookLoanDao.save(bookLoan);
            bookService.UpdateBook(requestedBook);
            userService.addBookLoanToUser(requestedUser, bookLoan);

            result = Boolean.TRUE;
        }
*/
        return result;
    }


    @Override
    public List<BookLoan> getAllBookLoans() {
        return bookLoanDao.findAll();
    }

    @Override
    public BookLoan getBookLoanById(Long id) {
        if(id!= null)
        {
            final Optional<BookLoan> optionalBookLoan = bookLoanDao.findById(id);
            if (optionalBookLoan.isPresent())
            {
                return optionalBookLoan.get();
            }
        }
        return null;
    }

    @Override
    public BookLoan UpdateBookLoan(BookLoan bookLoan) {
        return bookLoanDao.saveAndFlush(bookLoan);
    }

    @Override
    public void deleteBookLoan(BookLoan bookLoan) {
        bookLoanDao.delete(bookLoan);

    }

    @Override
    public List<BookLoan> getBookLoansByUser(User user) {
   /*     final User requestedUser = userService.findUserById(user.getId());
        if (requestedUser!= null)
        {
            //return requestedUser.getBookLoans();
        }*/
        return null;
    }
}
