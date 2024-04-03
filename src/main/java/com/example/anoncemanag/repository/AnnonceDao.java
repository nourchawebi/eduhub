package com.example.anoncemanag.repository;


import com.example.anoncemanag.entities.Annonce;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AnnonceDao extends JpaRepository<Annonce,Long> {
    @Query("SELECT a FROM Annonce a WHERE a.id_user = :idUser ORDER BY a.annonce_date DESC")
    List<Annonce> findAnnoncebyIdUser(long idUser);

}
