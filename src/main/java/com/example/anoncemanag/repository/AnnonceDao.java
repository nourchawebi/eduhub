package com.example.anoncemanag.repository;


import com.example.anoncemanag.entities.Annonce;
import com.example.anoncemanag.enums.TypeAnnonce;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AnnonceDao extends JpaRepository<Annonce,Long> {
    List<Annonce> findAnnonceByUserId(long idUser);

    List<Annonce> findByTypeAnnonceIn(List<TypeAnnonce> typeAnnonces);

    List<Annonce> findByTitle(String title);
}
