package com.esprit.cloudcraft.repository.userDao;

import com.esprit.cloudcraft.entities.userEntities.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
  Optional<User> findByEmail(String email);
//   user   findByUsername (String username);
Optional<User> findByTokens(List<String> tokens);


}
