package com.esprit.cloudcraft.implement;



import com.esprit.cloudcraft.entities.Comment;
import com.esprit.cloudcraft.entities.React;
import com.esprit.cloudcraft.repository.ReactDao;
import com.esprit.cloudcraft.services.IAnnonce;
import com.esprit.cloudcraft.repository.AnnonceDao;
import com.esprit.cloudcraft.repository.CommentDao;
import com.esprit.cloudcraft.entities.Annonce;
import com.esprit.cloudcraft.Enum.TypeAnnonce;
import com.esprit.cloudcraft.Enum.TypeInternship;
import jakarta.annotation.Resource;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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
    @Autowired
    private BadwordsService badwordsService;
    @Autowired
    private ReactDao reactDao;



    private final FileStorageServiceImp fileStorageServiceImp;

    public AnnonceService(FileStorageServiceImp fileStorageServiceImp) {
        this.fileStorageServiceImp = fileStorageServiceImp;
    }


    //private final String uploadAnnonceImages;

    /*public AnnonceService(ImageService imageService, @Value("${uploadAnnonceImages}") String uploadAnnonceImages) {
        this.imageService = imageService;
        this.uploadAnnonceImages = uploadAnnonceImages;
    }*/


    @Override
    public Annonce addAnnonce(String title, String annoncedesc, Date startDate,
                              TypeAnnonce typeAnnonce, MultipartFile image) {

        //Copier l'image vers le répertoire de destination
        // String fileName = imageService.nameFile(imageFile);
        // Path destinationPath = Paths.get(uploadAnnonceImages, fileName);
        // Files.copy(imageFile.getInputStream(), destinationPath);
        String sanitizedDesc = badwordsService.sanitizeText(annoncedesc);

        Annonce annonce1 = new Annonce();
        annonce1.setNbr_comment(0);
        annonce1.setAnnonce_date(LocalDate.now());
        annonce1.setAnnonce_description(sanitizedDesc);
        annonce1.setTypeAnnonce(typeAnnonce);
        annonce1.setPicture(fileStorageServiceImp.saveImage(image));
        annonce1.setStartDate(startDate);
        annonce1.setTitle(title);
        return annonceDao.save(annonce1);


    }

    @Override
    public List<Annonce> filterAnnonces(List<TypeAnnonce> typeAnnonces) {
        if (typeAnnonces != null) {
            return annonceDao.findByTypeAnnonceIn(typeAnnonces);
        } else {
            return null;
        }
    }

  /*  public Annonce addAnnonceSimple(Annonce annonce){

        return annonceDao.save(annonce);
    }*/
   /* public List<Annonce> getAnnoncesByUserId(long userId) {
        return annonceDao.findAnnonceByUserId(userId);
    }*/


    @Override
    public Optional<Annonce> getTargetAnnonce(long id) {

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
    public List<Annonce> searchAnnonce(String title) {

        return annonceDao.findByTitle(title);
    }

    @Override
    public Annonce addPost(String title, String annonceDescription, TypeAnnonce typeInternship) {
        String sanitizedDesc = badwordsService.sanitizeText(annonceDescription);
        Annonce post = Annonce.builder()


                .title(title)
                .annonce_description(sanitizedDesc)
                .typeAnnonce(typeInternship)
                .build();
        post.setNbr_comment(0);

        return annonceDao.save(post);
    }

    @Override
    public Annonce addIntership(String title, String annonceDescription, TypeAnnonce typeAnnonce, String governorate, Date date, TypeInternship typeInternship) {
        String sanitizedDesc = badwordsService.sanitizeText(annonceDescription);
        Annonce intership = Annonce.builder()
                .title(title)
                .annonce_description(sanitizedDesc)
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
        String sanitizedDesc = badwordsService.sanitizeText(annonceDescription);
        Annonce job = Annonce.builder()
                .title(title)
                .annonce_description(sanitizedDesc)
                .typeAnnonce(typeAnnonce)
                .startDate(date)
                .governorate(governorate)
                .build();
        job.setNbr_comment(0);

        return annonceDao.save(job);
    }




    @Override
    public List<Annonce> getAllAnnonce() {
        List<Annonce> annonces = annonceDao.findAll();
        // Pour chaque annonce, mettre à jour le nombre de commentaires
        for (Annonce annonce : annonces) {
            annonce.setNbr_comment(getNombreCommentaires(annonce));
        }
        return annonces;
    }
    @Override
    public int getNombreCommentaires(Annonce annonce) {
        // Récupérer la liste de commentaires associés à cette annonce
        List<Comment> comments = annonce.getComments();
        // Retourner le nombre de commentaires dans cette liste
        return (comments != null) ? comments.size() : 0;
    }

    @Override
    public void deleteAnnonce(long id_annonce) {
        Annonce annonceToDelete = annonceDao.findById(id_annonce).orElse(null);
        if (annonceToDelete != null) {
            // Supprimer toutes les réactions associées à cette annonce
            reactDao.deleteAllByAnnonce(annonceToDelete);

            // Supprimer l'annonce elle-même
            annonceDao.deleteById(id_annonce);
        } else {
            // Gérer le cas où l'annonce n'est pas trouvée
            // Peut-être lever une exception ou gérer d'une autre manière
        }
    }



            @Override

   public Annonce updateAnnonce(long id_annonce, String title, String annonceDescription){
       Annonce annonce1=annonceDao.findById(id_annonce).get();
       annonce1.setTitle(title);
       annonce1.setAnnonce_description(annonceDescription);
       return annonceDao.save(annonce1);

   }

    @Override
    public Annonce updateAnnnnonce(Annonce annonce) {
        Annonce annonce1=annonceDao.findById(annonce.getId_annonce()).get();
        annonce.setUser(annonce1.getUser());
        return annonceDao.save(annonce);

    }

    @Override
    public Map<String, Float> FindAnnonceByType() {
        List<Annonce> annonces = annonceDao.findAll();
            List<Annonce> internship = new ArrayList<>();
            List<Annonce> LostAndFound = new ArrayList<>();
            List<Annonce> JobOffer = new ArrayList<>();
            List<Annonce> Other = new ArrayList<>();

            for (Annonce a : annonces) {
                if (a.getTypeAnnonce() == TypeAnnonce.INTERNSHIP) {
                    internship.add(a);
                }  if (a.getTypeAnnonce() == TypeAnnonce.LOST_AND_FOUND) {
                    LostAndFound.add(a);
                }
             if (a.getTypeAnnonce() == TypeAnnonce.JOB_OFFER) {
            JobOffer.add(a);
        }
                else if (a.getTypeAnnonce() == TypeAnnonce.OTHER) {
                    Other.add(a);
                }
            }

            float pourcentINTERNSHIP = (float) internship.size() / annonces.size() * 100;
            float pourcentLostAndFound = (float) LostAndFound.size() / annonces.size() * 100;

            float pourcentJoboffer = (float) JobOffer.size() / annonces.size() * 100;
            float pourcentOther = (float) Other.size() / annonces.size() * 100;


        Map<String, Float> percentages = new HashMap<>();
            percentages.put("Pourcentage INTERNSHIP", pourcentINTERNSHIP);
            percentages.put("Pourcentage LostAnd Found", pourcentLostAndFound);
            percentages.put("Pourcentage JobOffer", pourcentJoboffer);
            percentages.put("Pourcentage Other", pourcentOther);
            return percentages;
        }


    @Override
    public List<Annonce> FindAnnonceByNblikes() {
        List<Map.Entry<Annonce, Integer>> top = new ArrayList<>();

        List<Annonce> annonces= annonceDao.findAnnonceBynbrLike();

        Map<Annonce, Integer> likesCount = new HashMap<>();
        for (Annonce a : annonces) {
            int likes = a.getLikes();
            likesCount.put(a, likes);
        }

        List<Map.Entry<Annonce, Integer>> sortedEntries = new ArrayList<>(likesCount.entrySet());
        sortedEntries.sort((entry1, entry2) -> entry2.getValue().compareTo(entry1.getValue()));

        top.add(sortedEntries.get(0));
        top.add(sortedEntries.get(1));
        top.add(sortedEntries.get(2));

        Annonce m1 = top.get(0).getKey();
        Annonce m2 = top.get(1).getKey();
        Annonce m3 = top.get(2).getKey();

        List<Annonce> top3Annonce = new ArrayList<>();
        top3Annonce.add(m1);
        top3Annonce.add(m2);
        top3Annonce.add(m3);

        return top3Annonce;
    }

    @Override
    public List<Annonce> FindAnnonceByDatePub(LocalDate date) {
        LocalDate date1 =LocalDate.now();
        return annonceDao.findAnnonceByDate(date1) ;
    }

    @Override
    public Annonce getAnnonceByid(long id_annonce) {
        return annonceDao.findById(id_annonce).get();
    }


}



