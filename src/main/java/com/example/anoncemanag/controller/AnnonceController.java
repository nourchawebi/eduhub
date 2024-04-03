package com.example.anoncemanag.controller;



import com.example.anoncemanag.entities.Annonce;
import com.example.anoncemanag.interfaces.IAnnonce;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/annonce")
@AllArgsConstructor
public class AnnonceController {
    @Autowired
    private final IAnnonce iAnnonce;
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Annonce addAnnonce(@RequestBody Annonce annonce) {
        return iAnnonce.addAnnonce(annonce);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Annonce> getAllAnnonce()  {
        return iAnnonce.getAllAnnonce();
    }

    @GetMapping("/{id_annonce}")
    public Optional<Annonce> getAnnonceByID(@PathVariable long id)  {
        return iAnnonce.getAnnonceByID(id);
    }

    @DeleteMapping("/{id_annonce}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteAnnonce (@PathVariable long id_annonce)  {
        iAnnonce.deleteAnnonce(id_annonce);

    }

    @GetMapping("/user")
    @ResponseStatus(HttpStatus.OK)
    public List<Annonce>getAnnonceByUser(@RequestParam long id_user) {
        return iAnnonce.getAnnonceByUser(id_user);
    }
    @PutMapping("/{id_annonce}")
    @ResponseStatus(HttpStatus.OK)
    public Annonce updateAnnonce(long id, Annonce updatedAnnonce) {
        return iAnnonce.updateAnnonce(id, updatedAnnonce);
    }

}
