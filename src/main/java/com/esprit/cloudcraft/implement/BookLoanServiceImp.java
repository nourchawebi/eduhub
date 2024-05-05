package com.esprit.cloudcraft.implement;

import com.esprit.cloudcraft.dto.BookBorrowResponse;
import com.esprit.cloudcraft.dto.BookResponse;
import com.esprit.cloudcraft.dto.PageResponse;
import com.esprit.cloudcraft.entities.AvailabilityType;
import com.esprit.cloudcraft.entities.Book;
import com.esprit.cloudcraft.entities.BookLoan;
import com.esprit.cloudcraft.entities.User;
import com.esprit.cloudcraft.repository.BookLoanDao;
import com.esprit.cloudcraft.services.*;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
    @Resource
    private EmailWithAttachmentService sendMail;
    @Resource
    private MapperService mapperService;

    @Override
    public Boolean addBookLoan(User user, Book book) {
         User requestedUser = userService.findUserById(user.getId());
         Book requestedBook = bookService.getBookByID(book.getIdBook());
        Boolean result = Boolean.FALSE;

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
            bookLoan.setReturned(Boolean.FALSE);
            //requestedBook.setAvailability(AvailabilityType.NOT_AVAILABILE);
            bookLoan.setUser(user);
            bookLoanDao.save(bookLoan);
            bookService.UpdateBook(requestedBook);
          //  userService.addBookLoanToUser(requestedUser, bookLoan);
            sendMail.sendEmailWithAttachment(requestedUser, bookLoan );

            result = Boolean.TRUE;
        }

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
    public PageResponse<BookBorrowResponse> findBookLoansByUser(int page, int size, User user) {
        Pageable pageable = PageRequest.of(page, size);
        Page<BookLoan> bookLoans = bookLoanDao.findAllByUser(pageable,user);
        List<BookBorrowResponse>bookBorrowResponses = bookLoans.stream()
                .map(mapperService::toBookBorrowResponse)
                .toList();
        System.out.println(bookBorrowResponses);
        return new PageResponse<>(
                bookBorrowResponses,
                bookLoans.getNumber(),
                bookLoans.getSize(),
                bookLoans.getTotalElements(),
                bookLoans.getTotalPages(),
                bookLoans.isFirst(),
                bookLoans.isLast()
        );

    }

    @Override
    public Boolean returnBookLoan(Long idBook) {
        BookLoan bookLoan = this.getBookLoanById(idBook);
        Book book = bookService.getBookByID(bookLoan.getBook().getIdBook());
        Boolean result = Boolean.FALSE;
        if(book !=null && bookLoan!=null)
        {
            book.setAvailability(AvailabilityType.AVAILABILE);
            bookService.UpdateBook(book);
            bookLoan.setReturned(Boolean.TRUE);
            bookLoanDao.save(bookLoan);
            result = Boolean.TRUE;
        }

        return result;
    }


}
