package com.example.carpooling.daos;

import com.example.carpooling.entities.Car;
import com.example.carpooling.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CarDao extends JpaRepository<Car,Integer> {

    @Query(value = "select distinct u from User u join Journey j on u.userId = j.motorized.userId")
    List<User> getMotorized();

}
