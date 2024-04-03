package com.example.anoncemanag.interfaces;



import com.example.anoncemanag.entities.Annonce;

import java.text.ParseException;
import java.util.List;
import java.util.Optional;

public interface IAnnonce {
    public Annonce addAnnonce(Annonce annonce);
    public Optional<Annonce> getAnnonceByID(long id);
    Annonce getTargetAnnonce(long id);
    public List<Annonce> getAnnonceByUser(long id_user);


    public Annonce updateAnnonce(long id, Annonce updatedAnnonce);

    public List<Annonce> getAllAnnonce();
    void deleteAnnonce(long id_annonce);
    }
