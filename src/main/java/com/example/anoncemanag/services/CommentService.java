package com.example.anoncemanag.services;


import com.example.anoncemanag.entities.Comment;
import com.example.anoncemanag.interfaces.IComment;
import com.example.anoncemanag.repository.CommentDao;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@AllArgsConstructor
@Transactional
@Service
public class CommentService implements IComment {
    @Autowired
    CommentDao commentRepository;

    @Override
    public Comment addComment(Comment comment) {
        return commentRepository.save(comment);
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
    public List<Comment> getAllComments() {
        return commentRepository.findAll();
    }
}
