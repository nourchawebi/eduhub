package com.example.anoncemanag.controller;



import com.example.anoncemanag.entities.Annonce;
import com.example.anoncemanag.enums.TypeAnnonce;
import com.example.anoncemanag.enums.TypeInternship;
import com.example.anoncemanag.interfaces.IAnnonce;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
    @PostMapping("/addPost")
    public ResponseEntity<Annonce> addPost(@RequestParam("title") String title,
                                              @RequestParam("annonce_description") String annonceDescription,
                                              @RequestParam("typeAnnonce") TypeAnnonce typeAnnonce
                                              ) {
        Annonce newAnnonce = iAnnonce.addPost(title, annonceDescription,typeAnnonce);
        return ResponseEntity.status(HttpStatus.CREATED).body(newAnnonce);
    }
    @PostMapping("/addIntership")
    public ResponseEntity<Annonce> addIntership(@RequestParam("title") String title,
                                           @RequestParam("annonce_description") String annonceDescription,
                                           @RequestParam("typeAnnonce") TypeAnnonce typeAnnonce,
                                                @RequestParam("governorate") String governorate,
                                                @RequestParam("date")  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date date,
                                           @RequestParam("typeintership") TypeInternship typeInternship
    ) {
        Annonce newAnnonce = iAnnonce.addIntership(title, annonceDescription,typeAnnonce,governorate,date,typeInternship);
        return ResponseEntity.status(HttpStatus.CREATED).body(newAnnonce);
    }

    @PostMapping("/addJobOffer")
    public ResponseEntity<Annonce> addJob(@RequestParam("title") String title,
                                          @RequestParam("annonce_description") String annonceDescription,
                                          @RequestParam("typeAnnonce") TypeAnnonce typeAnnonce,
                                          @RequestParam("governorate") String governorate,
                                          @RequestParam("date")  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date date
    ) {
        Annonce newAnnonce = iAnnonce.addJob(title, annonceDescription,typeAnnonce, governorate, date);
        return ResponseEntity.status(HttpStatus.CREATED).body(newAnnonce);
    }
    @PostMapping("/addAnnonce")
    public ResponseEntity<Annonce> addAnnonce(@RequestParam("title") String title,
                                              @RequestParam("annonce_description") String annonceDescription,
                                              @RequestParam("date")  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date date,                                              @RequestParam("typeAnnonce") TypeAnnonce typeAnnonce,
                                           //   @RequestParam("location") String governorate,
                                              @RequestParam("typeAnnonce") TypeAnnonce typeAnn,
                                              @RequestParam("file") MultipartFile imageFile) {
        Annonce newAnnonce = iAnnonce.addAnnonce(title, annonceDescription, date,typeAnn, imageFile);
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



    @GetMapping("/user/{id}")
    public List<Annonce> getAnnoncesByUser(@PathVariable("id") long id) {
        return iAnnonce.getAnnonceByUser(id);

    }
    @PutMapping("/{id_annonce}")
    @ResponseStatus(HttpStatus.OK)
    public Annonce updateAnnonce(long id, Annonce updatedAnnonce) {
        return iAnnonce.updateAnnonce(id, updatedAnnonce);
    }

}
