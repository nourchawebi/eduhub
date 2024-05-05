package com.esprit.cloudcraft.services;


import com.esprit.cloudcraft.entities.Comment;

import java.text.ParseException;
import java.util.List;

public interface IComment {

    Comment getCommentById(long id_comment);
    Comment updateComment(long id, String comment);
    void deleteComment(long id);
    public List<Comment> getAllComments(long id);


    List<Comment> getCommentByUser(long id_user);

    void addComment(long annonceId, String comment, long userId) throws ParseException;
}
