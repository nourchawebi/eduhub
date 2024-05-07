package com.esprit.cloudcraft.services;



import com.esprit.cloudcraft.entities.Annonce;
import com.esprit.cloudcraft.Enum.TypeAnnonce;
import com.esprit.cloudcraft.Enum.TypeInternship;
import com.esprit.cloudcraft.entities.userEntities.User;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface IAnnonce {
    public Annonce addAnnonce(String title, String annoncedesc, Date startDate,
                              TypeAnnonce typeAnnonce, MultipartFile imageFile,long id_user);
    //public Annonce addAnnonceSimple(Annonce annonce);
    int getNombreCommentaires(Annonce annonce);
    Optional<Annonce> getTargetAnnonce(long id);
    public List<Annonce> getAnnonceByUser(long id_user);


    //public Annonce updateAnnonce(long id, Annonce updatedAnnonce);

    public List<Annonce> getAllAnnonce();
    void deleteAnnonce(long id_annonce);

    public List<Annonce> filterAnnonces(List<TypeAnnonce> typeAnnonces);

    public List<Annonce> searchAnnonce(String title);

    Annonce addPost(String title, String annonceDescription, TypeAnnonce typeInternship,long id_user);

    Annonce addIntership(String title, String annonceDescription, TypeAnnonce typeAnnonce, String governorate, Date date, TypeInternship typeInternship,long id_user);

    Annonce addJob(String title, String annonceDescription, TypeAnnonce typeAnnonce, String governorate, Date date,long id_user);


    Annonce updateAnnonce(long id_annonce, String title, String annonceDescription);
    Annonce updateAnnnnonce(Annonce annonce);
    Map<String, Float> FindAnnonceByType();
    List<Annonce>FindAnnonceByNblikes();
    List<Annonce>FindAnnonceByDatePub(LocalDate date);

    Annonce getAnnonceByid(long id_annonce);


}
