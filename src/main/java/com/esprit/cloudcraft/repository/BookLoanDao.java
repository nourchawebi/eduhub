package com.esprit.cloudcraft.repository;

import com.esprit.cloudcraft.entities.BookLoan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookLoanDao extends JpaRepository<BookLoan,Long> {
}
