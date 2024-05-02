package com.esprit.cloudcraft.repository;

import com.esprit.cloudcraft.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.awt.print.Book;
import java.util.List;

@Repository
public interface UserDao extends JpaRepository<User, Long> {


}
