package com.example.anoncemanag.repository;

import com.example.anoncemanag.entities.React;
import com.example.anoncemanag.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao extends JpaRepository<User,Long> {
}
