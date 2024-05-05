package com.esprit.cloudcraft.repository;

import com.esprit.cloudcraft.entities.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentDao extends JpaRepository<Comment,Long> {
    List<Comment> findCommentByUserId(long idUser);

}
