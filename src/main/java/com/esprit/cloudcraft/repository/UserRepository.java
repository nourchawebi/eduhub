package com.esprit.cloudcraft.repository;

import com.esprit.cloudcraft.entities.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
  Optional<User> findByEmail(String email);
//   user   findByUsername (String username);
}
