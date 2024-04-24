package com.esprit.cloudcraft.Implement;

import com.esprit.cloudcraft.repository.CarDao;
import com.esprit.cloudcraft.repository.UserDao;
import com.esprit.cloudcraft.entities.Car;
import com.esprit.cloudcraft.entities.User;
import com.esprit.cloudcraft.services.CarService;
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
        car = carDao.save(car);
        User user = userDao.getReferenceById(1);
        user.getCars().add(car);
        userDao.save(user);
        return car;
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

        return userDao.getReferenceById(1).getCars();
    }

    @Override
    public List<User> getMotorizedUsers() {
        return carDao.getMotorized();
    }
}
