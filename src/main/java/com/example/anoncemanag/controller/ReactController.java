package com.example.anoncemanag.controller;

import com.example.anoncemanag.entities.React;
import com.example.anoncemanag.interfaces.IReact;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/reacts")
@CrossOrigin("*")
public class ReactController {

    @Autowired
    IReact reactInterface;

    @PostMapping("/addReact")
    public React addReact (@RequestBody React react){

        return reactInterface.addReact(react);
    }
    @PostMapping("/like")
    public void likePost(@RequestParam("annonce_id") long annonceId,@RequestParam ("user_id")long userId ) {
        reactInterface.likePost(annonceId,userId);
    }
    @PostMapping("/dislike")

    public void dislikePost(@RequestParam("annonce_id") long annonceId,@RequestParam ("user_id")long userId) {
        reactInterface.dislikePost(annonceId,userId);

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
