package com.esprit.cloudcraft.repository.userDao;

import com.esprit.cloudcraft.entities.userEntities.SecureToken;
import com.esprit.cloudcraft.entities.userEntities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SecureTokenRepository extends JpaRepository<SecureToken,Long> {
    SecureToken findByToken(String token);
     void removeByToken(String token);
     List<SecureToken> findAll();
   Optional< SecureToken >findByUser(User user);
}
