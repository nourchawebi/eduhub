package com.esprit.cloudcraft.repository;

import com.esprit.cloudcraft.entities.SecureToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SecureTokenRepository extends JpaRepository<SecureToken,Long> {
    SecureToken findByToken(String token);
     void removeByToken(String token);
     List<SecureToken> findAll();
}
