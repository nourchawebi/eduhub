package com.esprit.cloudcraft.controller;



import com.esprit.cloudcraft.entities.userEntities.User;
import com.esprit.cloudcraft.services.IAnnonce;
import com.esprit.cloudcraft.entities.Annonce;
import com.esprit.cloudcraft.Enum.TypeAnnonce;
import com.esprit.cloudcraft.Enum.TypeInternship;
import com.esprit.cloudcraft.services.FileStorageService;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.MalformedURLException;
import java.nio.file.Path;
import java.security.Principal;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@AllArgsConstructor
@CrossOrigin("*")
public class AnnonceController {
    @Autowired
    private final IAnnonce iAnnonce;
    @Autowired
    private  FileStorageService fileStorage;
    @PostMapping("/addPost")
    public ResponseEntity<Annonce> addPost(@RequestParam("title") String title,
                                              @RequestParam("annonce_description") String annonceDescription,
                                              @RequestParam("typeAnnonce") TypeAnnonce typeAnnonce,Principal connectedUser
                                              ) {
        var user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();
        Annonce newAnnonce = iAnnonce.addPost(title, annonceDescription,typeAnnonce, user.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(newAnnonce);
    }
    @PostMapping("/addIntership")
    public ResponseEntity<Annonce> addIntership(@RequestParam("title") String title,
                                           @RequestParam("annonce_description") String annonceDescription,
                                           @RequestParam("typeAnnonce") TypeAnnonce typeAnnonce,
                                                @RequestParam("governorate") String governorate,
                                                @RequestParam("date")  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date date,
                                           @RequestParam("typeintership") TypeInternship typeInternship,Principal connectedUser
    ) {
        var user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();
        Annonce newAnnonce = iAnnonce.addIntership(title, annonceDescription,typeAnnonce,governorate,date,typeInternship, user.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(newAnnonce);
    }

    @PostMapping("/addJobOffer")
    public ResponseEntity<Annonce> addJob(@RequestParam("title") String title,
                                          @RequestParam("annonce_description") String annonceDescription,
                                          @RequestParam("typeAnnonce") TypeAnnonce typeAnnonce,
                                          @RequestParam("governorate") String governorate,
                                          @RequestParam("date")  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date date,Principal connectedUser

    ) {
        var user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();
        Annonce newAnnonce = iAnnonce.addJob(title, annonceDescription,typeAnnonce, governorate, date, user.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(newAnnonce);
    }
    @PostMapping(value ="/addAnnonce",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Annonce> addAnnonce(@RequestParam("title") String title,
                                              @RequestParam("annonce_description") String annonceDescription,
                                              @RequestParam("date")  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date date,                                              @RequestParam("typeAnnonce") TypeAnnonce typeAnnonce,
                                              @RequestParam("typeAnnonce") TypeAnnonce typeAnn,
                                              @RequestParam("file") MultipartFile picture ,Principal connectedUser){
        var user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();
        Annonce newAnnonce = iAnnonce.addAnnonce(title, annonceDescription, date,typeAnn, picture, user.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(newAnnonce);
    }
  /*  @PostMapping("/addAnnonceSimple")
    public Annonce addAnnonceSimple(@RequestBody Annonce annonce) {
        return iAnnonce.addAnnonceSimple(annonce);
    }
*/





    @GetMapping("/filtrer")
    public List<Annonce> filterAnnonces(@RequestParam("typeAnnonces") List<TypeAnnonce> typeAnnonces){
        return  iAnnonce.filterAnnonces(typeAnnonces);
    }

    @GetMapping("/search/{title}")
    public List<Annonce> searchAnnonces(@PathVariable("title") String title) {
        return iAnnonce.searchAnnonce(title);
    }


   @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Annonce> getAllAnnonce()  {
        return iAnnonce.getAllAnnonce();
    }





    @GetMapping("/{id_annonce}")
    public ResponseEntity<Annonce> getTargetAnnonce(@PathVariable("id_annonce") long id) {
        Optional<Annonce> annonce = iAnnonce.getTargetAnnonce(id);
        return annonce.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }


    @DeleteMapping("/{id_annonce}")
    public ResponseEntity<Void> deleteAnnonce(@PathVariable("id_annonce") long id_annonce) {


        Optional<Annonce> anononce = iAnnonce.getTargetAnnonce(id_annonce);

        if (anononce.isPresent()) {
        iAnnonce.deleteAnnonce(id_annonce);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }



    @GetMapping("/annonceByUser")
    public List<Annonce> getAnnoncesByUser(Principal connectedUser) {
        var user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();
        return iAnnonce.getAnnonceByUser(user.getId());

    }



  /*  @PutMapping("/{id_annonce}")
    @ResponseStatus(HttpStatus.OK)
    public Annonce updateAnnonce(long id, Annonce updatedAnnonce) {
        return iAnnonce.updateAnnonce(id, updatedAnnonce);
    }*/



    @GetMapping("/image/{fileName}")
    public ResponseEntity<Resource> getImage(@PathVariable String fileName)    {
        System.out.println(fileName);
        // Construct the file path
        Path filePath =fileStorage.getImagePath(fileName);
        Resource resource = null;
        try {
            resource = new UrlResource(filePath.toUri());
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
        // Check if the file exists
        if (!resource.exists()) {
            return ResponseEntity.notFound().build();
        }

        // Set content type as application/pdf
        HttpHeaders headers = new HttpHeaders();
        String contentType;
        if (fileName.toLowerCase().endsWith(".pdf")) {
            contentType = MediaType.APPLICATION_PDF_VALUE;
        } else if (fileName.toLowerCase().endsWith(".png") || fileName.toLowerCase().endsWith(".jpg") || fileName.toLowerCase().endsWith(".jpeg")) {
            contentType = MediaType.IMAGE_JPEG_VALUE; // You can use IMAGE_PNG_VALUE for PNG files
        } else {
            // Default to binary data if the file type is unknown
            contentType = MediaType.APPLICATION_OCTET_STREAM_VALUE;
        }
        headers.add(HttpHeaders.CONTENT_TYPE, contentType);

        // Return the file/image as ResponseEntity with appropriate headers
        return ResponseEntity.ok()
                .headers(headers)
                .body(resource);
    }

    @PutMapping("/updateAnnonce/{id}")
    public Annonce updateAnnonce(@PathVariable("id") long id, @RequestParam("title") String title, @RequestParam("annonce_description") String annonceDescription) {
        return iAnnonce.updateAnnonce(id, title, annonceDescription);
    }
    @PutMapping("/updateAnnonceee")
    public Annonce updateAnnonnnnnce(@RequestBody Annonce annonce) {
        return iAnnonce.updateAnnnnonce(annonce);
    }
    @GetMapping("/retrieve-Annonce")

    public Map<String, Float> retrieveAnnonceByType() {
        return iAnnonce.FindAnnonceByType();
    }
    @GetMapping("/retrieve-MustLike")

    public List<Annonce> retrieveAnnonceByNbrLikes(){
        return iAnnonce.FindAnnonceByNblikes();
    }
    @GetMapping("/retrieve-AnnonceDate/{date}")

    public List<Annonce> retrieveAnnonceByDate(@PathVariable("date") @DateTimeFormat(pattern = "yyyy-MM-dd")LocalDate date) {
        return iAnnonce.FindAnnonceByDatePub(date);
    }

    @GetMapping("/annonce/{idA}")
    public Annonce getAnnonceByid(@PathVariable("idA") long id_annonce) {
        return iAnnonce.getAnnonceByid(id_annonce);

    }
}
