package com.esprit.cloudcraft.repository;

import com.esprit.cloudcraft.entities.Book;
import com.esprit.cloudcraft.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookDao extends JpaRepository<Book, Long> {
    List<Book> findBookByCategory(Category category);
}
