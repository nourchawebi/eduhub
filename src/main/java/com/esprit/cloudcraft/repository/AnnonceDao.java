package com.esprit.cloudcraft.repository;


import com.esprit.cloudcraft.entities.Annonce;
import com.esprit.cloudcraft.Enum.TypeAnnonce;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface AnnonceDao extends JpaRepository<Annonce,Long> {
    List<Annonce> findAnnonceByUserId(long idUser);

    List<Annonce> findByTypeAnnonceIn(List<TypeAnnonce> typeAnnonces);

    List<Annonce> findByTitle(String title);
    //@Query("SELECT a FROM Annonce a WHERE a.typeAnnonce= :type ")
    //List<Annonce> findAnnonceByType(@Param("type")TypeAnnonce typeAnnonce);
    @Query("SELECT a FROM Annonce a order by a.likes DESC ")
    List<Annonce> findAnnonceBynbrLike();

    @Query("SELECT a FROM Annonce a WHERE a.annonce_date= :date ")
    List<Annonce> findAnnonceByDate(@Param("date")LocalDate date);

}
