package com.example.anoncemanag.controller;



import com.example.anoncemanag.entities.Annonce;
import com.example.anoncemanag.enums.TypeAnnonce;
import com.example.anoncemanag.enums.TypeInternship;
import com.example.anoncemanag.interfaces.IAnnonce;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@CrossOrigin("*")
public class AnnonceController {
    @Autowired
    private final IAnnonce iAnnonce;
    @PostMapping("/addAnnonce")
    public ResponseEntity<Annonce> addAnnonce(@RequestParam("title") String title,
                                              @RequestParam("annonce_description") String annonceDescription,
                                              @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
                                              @RequestParam("typeAnnonce") TypeAnnonce typeAnnonce,
                                              //@RequestParam("location") String location,
                                             // @RequestParam("typeInternship") TypeInternship typeInternship,
                                              @RequestParam("file") MultipartFile imageFile) {
        Annonce newAnnonce = iAnnonce.addAnnonce(title, annonceDescription, startDate, typeAnnonce, imageFile);
        return ResponseEntity.status(HttpStatus.CREATED).body(newAnnonce);
    }
    @PostMapping("/addAnnonceSimple")

    public ResponseEntity<Annonce> addAnnonceSimple(@RequestParam("title") String title,
                                              @RequestParam("annonce_description") String annonceDescription,
                                              @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
                                              @RequestParam("typeAnnonce") TypeAnnonce typeAnnonce,

                                                    @RequestParam("typeInternship") TypeInternship typeInternship){
        Annonce newAnnonce = iAnnonce.addAnnonceSimple(title, annonceDescription, startDate, typeAnnonce, typeInternship);
        return ResponseEntity.status(HttpStatus.CREATED).body(newAnnonce);
}

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
