package com.example.anoncemanag.services;



import com.example.anoncemanag.entities.Annonce;
import com.example.anoncemanag.enums.TypeAnnonce;
import com.example.anoncemanag.enums.TypeInternship;
import com.example.anoncemanag.interfaces.IAnnonce;
import com.example.anoncemanag.repository.AnnonceDao;
import com.example.anoncemanag.repository.CommentDao;
import jakarta.annotation.Resource;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.*;

@Service
@Transactional

public class AnnonceService implements IAnnonce {
    @Resource
    @Autowired
    AnnonceDao annonceDao;
    @Autowired
   CommentDao commentRepository;


    private final ImageService imageService;

    private final String uploadAnnonceImages;

    public AnnonceService(ImageService imageService, @Value("${uploadAnnonceImages}") String uploadAnnonceImages) {
        this.imageService = imageService;
        this.uploadAnnonceImages = uploadAnnonceImages;
    }


    @Override
    public Annonce addAnnonce(String title, String annoncedesc, Date startDate,
                              TypeAnnonce typeAnnonce, MultipartFile imageFile) {
        try {
             //Copier l'image vers le répertoire de destination
            String fileName = imageService.nameFile(imageFile);
            Path destinationPath = Paths.get(uploadAnnonceImages, fileName);
            Files.copy(imageFile.getInputStream(), destinationPath);

            Annonce annonce1 = new Annonce();
            annonce1.setNbr_comment(0);
            annonce1.setAnnonce_date(LocalDate.now());
            annonce1.setAnnonce_description(annoncedesc);
            annonce1.setTypeAnnonce(typeAnnonce);
           annonce1.setImage(fileName.toString());
            annonce1.setStartDate(startDate);
            annonce1.setTitle(title);
            return annonceDao.save(annonce1);
        } catch (IOException e) {
            throw new RuntimeException("Failed to upload image", e);
        }

    }

    @Override
    public List<Annonce> filterAnnonces(List<TypeAnnonce> typeAnnonces) {
        if(typeAnnonces != null) {
            return annonceDao.findByTypeAnnonceIn(typeAnnonces);
        } else {
            return null;
        }
    }

    public Annonce addAnnonceSimple(Annonce annonce){

        return annonceDao.save(annonce);
    }
    public List<Annonce> getAnnoncesByUserId(long userId) {
        return annonceDao.findAnnonceByUserId(userId);
    }



    @Override
    public  Optional<Annonce> getTargetAnnonce(long id) {

        return annonceDao.findById(id);
    }
   /* public Optional<Annonce> getAnnonceById(long id) {
        try {
            return annonceDao.findById(id);
        } catch (Exception e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }*/

    @Override
    public List<Annonce> getAnnonceByUser(long id_user) {
        List<Annonce> annonces = annonceDao.findAnnonceByUserId(id_user);

        return annonces;
    }

    @Override
    public List<Annonce> searchAnnonce(String title){

        return annonceDao.findByTitle(title);
    }

    @Override
    public Annonce addPost(String title, String annonceDescription, TypeAnnonce typeInternship) {
        Annonce post = Annonce.builder()


                .title(title)
                .annonce_description(annonceDescription)
                .typeAnnonce(typeInternship)
                .build();
        post.setNbr_comment(0);

        return annonceDao.save(post);
    }

    @Override
    public Annonce addIntership(String title, String annonceDescription, TypeAnnonce typeAnnonce, String governorate, Date date, TypeInternship typeInternship) {
        Annonce intership = Annonce.builder()
                .title(title)
                .annonce_description(annonceDescription)
                .typeAnnonce(typeAnnonce)
                .startDate(date)
                .governorate(governorate)
                .typeInternship(typeInternship)
                .build();
        intership.setNbr_comment(0);
        return annonceDao.save(intership);
    }

    @Override
    public Annonce addJob(String title, String annonceDescription, TypeAnnonce typeAnnonce, String governorate, Date date) {
        Annonce job = Annonce.builder()
                .title(title)
                .annonce_description(annonceDescription)
                .typeAnnonce(typeAnnonce)
                .startDate(date)
                .governorate(governorate)
                .build();
        job.setNbr_comment(0);

        return annonceDao.save(job);    }

    @Override
   public Annonce updateAnnonce(long id, Annonce updatedAnnonce) {
        if (annonceDao.existsById((long) Math.toIntExact(id))){

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
