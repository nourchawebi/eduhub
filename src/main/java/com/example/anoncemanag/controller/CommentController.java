package com.example.anoncemanag.controller;

import com.example.anoncemanag.entities.Comment;
import com.example.anoncemanag.interfaces.IComment;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/comments")
public class CommentController {
    @Autowired
    IComment commentInterface;

    @PostMapping("/addComment")
    public Comment addComment (@RequestBody Comment comment){

        return commentInterface.addComment(comment);
    }
    @PutMapping("/updateComment/{id}")
    public Comment updateComment(@PathVariable Long id, @RequestBody Comment comment) {
        return commentInterface.updateComment(id,comment);
    }


    @DeleteMapping("/deleteComment/{id}")
    public Void deleteComment(@PathVariable long id) {
        this.commentInterface.deleteComment(id);
        return null;
    }


    @GetMapping("/getAllComments")
    public List<Comment> getAllComments() {

        return commentInterface.getAllComments();

    }
    @GetMapping("/getCommentById{id}")
    public Comment getCommentById(@PathVariable Long id) {

        return commentInterface.getCommentById(id);
    }

    }

