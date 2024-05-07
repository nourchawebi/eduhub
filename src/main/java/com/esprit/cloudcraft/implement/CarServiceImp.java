package com.esprit.cloudcraft.implement;

import com.esprit.cloudcraft.entities.userEntities.User;
import com.esprit.cloudcraft.repository.CarDao;
import com.esprit.cloudcraft.repository.JourneyDao;
import com.esprit.cloudcraft.repository.userDao.UserRepository;
import com.esprit.cloudcraft.entities.Car;
import com.esprit.cloudcraft.entities.userEntities.User;
import com.esprit.cloudcraft.serviceInt.CarService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarServiceImp implements CarService {
    @Resource
    CarDao carDao;
    @Resource
    UserRepository userDao;

    @Resource
    JourneyDao journeyDao;

    @Override
    public Car addCar(Car car, User user) {
        car = carDao.save(car);
        User u = userDao.getReferenceById(user.getId());
        u.getCars().add(car);
        userDao.save(u);
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
        Car car;
        User user;
        if(carDao.findById(carId).isPresent())
            {
                car = carDao.findById(carId).get();
                user = userDao.findByCarsContains(car);

                    List<Car> cars = user.getCars();
                    cars.remove(car);
                    user.setCars(cars);
                    userDao.save(user);


                carDao.deleteById(carId);
            }
    }

    @Override
    public List<Car> getCarsByMotorized(User motorized) {

        return userDao.getReferenceById(motorized.getId()).getCars();
    }

    @Override
    public List<User> getMotorizedUsers() {
        return carDao.getMotorized();
    }
}
