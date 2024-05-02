package com.example.anoncemanag.controller;

import com.example.anoncemanag.dto.CommentDto;
import com.example.anoncemanag.entities.Annonce;
import com.example.anoncemanag.entities.Comment;
import com.example.anoncemanag.interfaces.IComment;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/comments")
@CrossOrigin("*")
public class CommentController {
    @Autowired
    IComment commentInterface;

    @PostMapping("/addComment")
    public void addComment(@RequestParam("annonce_id") long annonceId,@RequestParam("comment_text") String comment,
                              @RequestParam("id_user") long userId) throws ParseException {

           commentInterface.addComment(annonceId, comment,userId);


    }


    @PutMapping("/updateComment/{id}")
    public Comment updateComment(@PathVariable Long id, @RequestParam("comment_text") String comment) {
        return commentInterface.updateComment(id,comment);
    }


    @DeleteMapping("/deleteComment/{id}")
    public Void deleteComment(@PathVariable long id) {
        this.commentInterface.deleteComment(id);
        return null;
    }


    @GetMapping("/getAllComments/{id}")
    public List<Comment> getAllComments(@PathVariable long id) {
        return commentInterface.getAllComments(id);


    }
    @GetMapping("/getCommentById{id}")
    public Comment getCommentById(@PathVariable Long id) {

        return commentInterface.getCommentById(id);
    }
    @GetMapping("/user/{id}")
    public List<Comment> getCommentByUser(@PathVariable("id") long id) {
        return commentInterface.getCommentByUser(id);

    }

    }


