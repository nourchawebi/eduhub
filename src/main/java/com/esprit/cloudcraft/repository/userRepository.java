package com.esprit.cloudcraft.repository;

import com.esprit.cloudcraft.entities.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface userRepository extends JpaRepository<user,Long> {
   user findByEmail(String email);
//   user   findByUsername (String username);
}
