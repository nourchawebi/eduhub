package com.esprit.cloudcraft.repository;

import com.esprit.cloudcraft.entities.AvailabilityType;
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
    //Statistiques
    // Nombre total de livres dans la bibliothèque
    @Query("SELECT COUNT(b) FROM Book b")
    long count();

    // Répartition des livres par catégorie
    @Query("SELECT b.category.name, COUNT(b) FROM Book b GROUP BY b.category")
    List<Object[]> countBooksByCategory();
    //Nombre de books disponibles
    long countBooksByAvailability(AvailabilityType availability);

    // Évolution du nombre de livres au fil du temps (par mois)
    @Query("SELECT MONTH(b.publicationDate) AS month, COUNT(b) " +
            "FROM Book b " +
            "WHERE YEAR(b.publicationDate) = YEAR(CURRENT_DATE()) " +
            "GROUP BY MONTH(b.publicationDate)")
    List<Object[]> countBooksByCreationMonthOfCurrentYear();

}
