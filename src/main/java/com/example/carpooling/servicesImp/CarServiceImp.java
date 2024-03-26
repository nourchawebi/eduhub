package com.example.carpooling.servicesImp;

import com.example.carpooling.daos.CarDao;
import com.example.carpooling.daos.UserDao;
import com.example.carpooling.entities.Car;
import com.example.carpooling.entities.User;
import com.example.carpooling.services.CarService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarServiceImp implements CarService {
    @Resource
    CarDao carDao;
    @Resource
    UserDao userDao;

    @Override
    public Car addCar(Car car) {
        car.setMotorized(userDao.getReferenceById(1));
        return carDao.save(car);
    }

    @Override
    public Car modifyCar(Integer carId, Car car) {
        if(carId!=null)
            if(carDao.existsById(carId)){
                car.setCarId(carId);
                return carDao.save(car);
            }
        return null;
    }

    @Override
    public List<Car> getAllCars() {
        return carDao.findAll();
    }

    @Override
    public void deleteCarById(Integer carId) {
        if(carDao.existsById(carId))
            carDao.deleteById(carId);
    }

    @Override
    public List<Car> getCarsByMotorized() {
        User user = userDao.getReferenceById(1);
        System.out.println(user);
        return carDao.getCarsByMotorized(user);
    }

    @Override
    public List<User> getMotorizedUsers() {
        return carDao.getMotorized();
    }
}
