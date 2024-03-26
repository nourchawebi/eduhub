package com.example.carpooling.services;

import com.example.carpooling.entities.Car;
import com.example.carpooling.entities.User;

import java.util.List;

public interface CarService {
    Car addCar(Car car);
    Car modifyCar(Integer carId, Car car);

    List<Car> getAllCars();
    void deleteCarById(Integer carId);

    List<Car> getCarsByMotorized();

    List<User> getMotorizedUsers();
}
