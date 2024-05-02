package com.example.anoncemanag.repository;

import com.example.anoncemanag.entities.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CommentDao extends JpaRepository<Comment,Long> {
    List<Comment> findCommentByUserId(long idUser);

}
