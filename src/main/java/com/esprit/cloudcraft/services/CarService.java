package com.esprit.cloudcraft.services;

import com.esprit.cloudcraft.entities.Car;
import com.esprit.cloudcraft.entities.User;

import java.util.List;

public interface CarService {
    Car addCar(Car car);
    Car modifyCar(Integer carId, Car car);

    List<Car> getAllCars();
    void deleteCarById(Integer carId);

    List<Car> getCarsByMotorized();

    List<User> getMotorizedUsers();
}
