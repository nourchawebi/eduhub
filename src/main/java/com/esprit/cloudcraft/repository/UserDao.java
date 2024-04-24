package com.esprit.cloudcraft.repository;

import com.esprit.cloudcraft.entities.Participation;
import com.esprit.cloudcraft.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserDao extends JpaRepository<User,Integer> {
    List<User> getUserByParticipationsIn(List<Participation> participations);
}
