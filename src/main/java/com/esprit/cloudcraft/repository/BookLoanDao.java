package com.esprit.cloudcraft.repository;

import com.esprit.cloudcraft.entities.BookLoan;
import com.esprit.cloudcraft.entities.userEntities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookLoanDao extends JpaRepository<BookLoan,Long> {
    Page<BookLoan>findAllByUser(Pageable pageable, User user);
}
