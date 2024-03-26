package com.example.carpooling.controllers;

import com.example.carpooling.entities.Car;
import com.example.carpooling.entities.User;
import com.example.carpooling.services.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class CarController {
    @Autowired
    CarService carService;

    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping("car")
    @ResponseBody
    public Car addCar(@RequestBody Car car){
        return carService.addCar(car);
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("motorized_cars")
    @ResponseBody
    public List<Car> getMotorizedCars(){
        return carService.getCarsByMotorized();
    }

    @GetMapping("car")
    @ResponseBody
    public List<Car> getAllCars(){
        return carService.getAllCars();
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("motorized")
    @ResponseBody
    public List<User> getMotorizedUsers(){
        return carService.getMotorizedUsers();
    }

    @PutMapping("car/{id}")
    @ResponseBody
    public Car modifyCar(@PathVariable("id") Integer id, @RequestBody Car car){
        return carService.modifyCar(id,car);
    }

    @DeleteMapping("car/{id}")
    @ResponseBody
    public void deleteCarById(@PathVariable("id") Integer id){
        carService.deleteCarById(id);
    }
}
