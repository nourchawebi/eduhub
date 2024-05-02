package com.esprit.cloudcraft.repository;

import com.esprit.cloudcraft.entities.Book;
import com.esprit.cloudcraft.entities.Category;
import com.esprit.cloudcraft.entities.User;
import org.apache.el.lang.ELArithmetic;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookDao extends JpaRepository<Book, Long> {
    Page<Book> findBookByUser(Pageable pageable, User user);
    @Query("SELECT b FROM Book b WHERE b.user <> :user AND b.availability = 'AVAILABILE'")
    Page<Book> findAllAvailableBooksExceptUser(Pageable pageable, User user);

}
