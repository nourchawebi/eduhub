package com.example.anoncemanag.repository;

import com.example.anoncemanag.entities.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentDao extends JpaRepository<Comment,Long> {
}
