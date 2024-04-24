package com.example.anoncemanag.interfaces;


import com.example.anoncemanag.entities.Comment;

import java.util.List;

public interface IComment {
    public Comment addComment(Comment comment,long annonceId);
    Comment getCommentById(long id);
    Comment updateComment(long id, Comment comment);
    void deleteComment(long id);
    public List<Comment> getAllComments(long id);




}
