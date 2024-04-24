package com.example.anoncemanag.interfaces;



import com.example.anoncemanag.entities.Annonce;
import com.example.anoncemanag.enums.TypeAnnonce;
import com.example.anoncemanag.enums.TypeInternship;
import org.springframework.web.multipart.MultipartFile;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface IAnnonce {
    public Annonce addAnnonce(String title, String annoncedesc,  LocalDate startDate,
                              TypeAnnonce typeAnnonce,MultipartFile imageFile);
    public Annonce addAnnonceSimple(String title, String annoncedesc, LocalDate startDate,
                                    TypeAnnonce typeAnnonce,TypeInternship typeInternship);

    Optional<Annonce> getTargetAnnonce(long id);
    public List<Annonce> getAnnonceByUser(long id_user);


    public Annonce updateAnnonce(long id, Annonce updatedAnnonce);

    public List<Annonce> getAllAnnonce();
    void deleteAnnonce(long id_annonce);

    public List<Annonce> filterAnnonces(List<TypeAnnonce> typeAnnonces);

    public List<Annonce> searchAnnonce(String title);
    }
