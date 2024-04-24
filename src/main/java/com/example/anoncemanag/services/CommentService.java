package com.example.anoncemanag.services;


import com.example.anoncemanag.entities.Annonce;
import com.example.anoncemanag.entities.Comment;
import com.example.anoncemanag.interfaces.IComment;
import com.example.anoncemanag.repository.AnnonceDao;
import com.example.anoncemanag.repository.CommentDao;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
@AllArgsConstructor
@Transactional
@Service
public class CommentService implements IComment {
    @Autowired
    CommentDao commentRepository;
    @Autowired
    AnnonceDao annonceDao;

    @Override
    public Comment addComment(Comment comment, long annonceId) {
        Annonce existingAnnonce = annonceDao.findById(annonceId).orElse(null);
        if (existingAnnonce == null) {

            return null;
        }
        comment.setComment_date(LocalDate.now());
        comment.setAnnonce(existingAnnonce);
        existingAnnonce.getComments().add(comment);
        annonceDao.save(existingAnnonce);
        return comment ;
    }




    @Override
    public Comment getCommentById(long id) {
        return commentRepository.findById(id).orElse(null);
    }

    @Override

    public Comment updateComment(long id, Comment comment){
        if (commentRepository.existsById((long) Math.toIntExact(id))){
            comment.setId_comment(id);
            return commentRepository.save(comment);

        }
        return null;
    }

    @Override
    public void deleteComment(long id) {

        commentRepository.deleteById(id);
    }

    @Override
    public List<Comment> getAllComments(long id) {
        {
            Annonce annonces=annonceDao.findById(id).get();
            List<Comment>comments=annonces.getComments();
            return comments;
        }
    }


}
