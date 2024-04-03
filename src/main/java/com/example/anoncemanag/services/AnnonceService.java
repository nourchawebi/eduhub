package com.example.anoncemanag.services;



import com.example.anoncemanag.entities.Annonce;
import com.example.anoncemanag.interfaces.IAnnonce;
import com.example.anoncemanag.repository.AnnonceDao;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor

public class AnnonceService implements IAnnonce {
    @Resource
    @Autowired
    AnnonceDao annonceDao;



    @Override
    public Annonce addAnnonce(Annonce annonce) {
        return annonceDao.save(annonce);

    }

    @Override
    public Optional<Annonce> getAnnonceByID(long id) {
        return annonceDao.findById(id);


    }

    @Override
    public Annonce getTargetAnnonce(long id) {
        return annonceDao.findById(id).get();
    }

    @Override
    public List<Annonce> getAnnonceByUser(long id_user) {
        List<Annonce> annonces = annonceDao.findAnnoncebyIdUser(id_user);

        return annonces;
    }

    @Override
    public Annonce updateAnnonce(long id, Annonce updatedAnnonce) {
        if (annonceDao.existsById((long) Math.toIntExact(id))){
            updatedAnnonce.setId_annonce(id);
            return annonceDao.save(updatedAnnonce);

        }
        return null;
    }


    @Override
    public List<Annonce> getAllAnnonce() {
        List<Annonce> annonces = annonceDao.findAll();
        return annonces;

    }

    @Override
    public void deleteAnnonce(long id_annonce) {
        annonceDao.deleteById(id_annonce);

    }
}
