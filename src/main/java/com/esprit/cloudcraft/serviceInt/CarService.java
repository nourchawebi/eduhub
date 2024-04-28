package com.esprit.cloudcraft.serviceInt;

import com.esprit.cloudcraft.entities.Car;
import com.esprit.cloudcraft.entities.userEntities.User;

import java.util.List;

public interface CarService {
    Car addCar(Car car, User user);
    Car modifyCar(Integer carId, Car car);

    List<Car> getAllCars();
    void deleteCarById(Integer carId);

    List<Car> getCarsByMotorized(User motorized);

    List<User> getMotorizedUsers();
}
