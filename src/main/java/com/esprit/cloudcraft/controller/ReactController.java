package com.esprit.cloudcraft.controller;

import com.esprit.cloudcraft.entities.userEntities.User;
import com.esprit.cloudcraft.services.IReact;
import com.esprit.cloudcraft.entities.React;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@AllArgsConstructor
@RequestMapping("/api/reacts")
@CrossOrigin(origins = "http://localhost:4200")
public class ReactController {

    @Autowired
    IReact reactInterface;

    @PostMapping("/addReact")
    public React addReact (@RequestBody React react){
        ;

        return reactInterface.addReact(react);
    }
    @PostMapping("/like")
    public void likePost(@RequestParam("annonce_id") long annonceId,Principal connectedUser) {
        var user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();
        reactInterface.likePost(annonceId,user.getId());
    }
    @PostMapping("/dislike")

    public void dislikePost(@RequestParam("annonce_id") long annonceId,Principal connectedUser) {
        var user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();
        reactInterface.dislikePost(annonceId,user.getId());

    }
    @GetMapping("/nbrLikesParAnnonce/{annonceId}")
    public int nbrLikesParAnnonce(@PathVariable long annonceId) {

        return reactInterface.nbrLikesParAnnonce(annonceId);
    }

    @GetMapping("/nbrDislikesParAnnonce/{annonceId}")
    public int nbrDislikesParAnnonce(@PathVariable long annonceId) {
        return reactInterface.nbrDislikesParAnnonce(annonceId);
    }


   /* @PutMapping("/updateReact/{id}")
    public React updateReact(@PathVariable Long id, @RequestBody React react) {
        return reactInterface.updateReact(id,react);
    }*/

   /* @DeleteMapping("/deleteReact/{id}")
    public Void deleteReact(@PathVariable long id) {
        this.reactInterface.deleteReact(id);
        return null;
    }*/

   /* @GetMapping("/getAllReacts")
    public List<React> getAllReact() {

        return reactInterface.getAllReact();

    }*/
   /* @GetMapping("/getReactById{id}")
    public React getReactById(@PathVariable Long id) {

        return reactInterface.getReactById(id);
    }*/

    @GetMapping("/verifyUserReaction")
    public boolean verifyUserReactionLike(@RequestParam long userId, @RequestParam Long annonceId) {
        return reactInterface.verifyUserReactionLike(userId, annonceId);
    }

}
