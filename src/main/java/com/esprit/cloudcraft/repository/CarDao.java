package com.esprit.cloudcraft.repository;

import com.esprit.cloudcraft.entities.Car;
import com.esprit.cloudcraft.entities.userEntities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CarDao extends JpaRepository<Car,Integer> {

    @Query(value = "select distinct u from User u join Journey j on u.id = j.motorized.id")
    List<User> getMotorized();

}
