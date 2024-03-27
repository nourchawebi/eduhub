package com.example.carpooling.servicesImp;

import com.example.carpooling.daos.LocationDao;
import com.example.carpooling.daos.UserDao;
import com.example.carpooling.entities.Location;
import com.example.carpooling.entities.User;
import com.example.carpooling.services.LocationService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LocationServiceImp implements LocationService {
    @Resource
    LocationDao locationDao;
    @Resource
    UserDao userDao;

    @Override
    public User addLocation(Integer userId, Location location) {
        if(userId!=null)
            if(userDao.existsById(userId))
                if(userDao.getReferenceById(userId).getLocation()==null)
                    {
                        User user = userDao.getReferenceById(userId);
                        user.setLocation(location);
                        return userDao.save(user);
                    }
        return null;
    }

    @Override
    public User updateLocation(Integer userId, Location location) {
        if(userId!=null)
            if(userDao.existsById(userId))
                if(userDao.getReferenceById(userId).getLocation()!=null)
                {
                    User user = userDao.getReferenceById(userId);
                    user.setLocation(location);
                    return userDao.save(user);
                }
        return null;
    }

    @Override
    public List<Location> getUsersLocation() {
        if(!locationDao.findAll().isEmpty())
            return locationDao.findAll();
        return null;
    }

    @Override
    public void deleteLocation(Integer locationId) {
        if(locationDao.existsById(locationId))
            locationDao.delete(locationDao.getReferenceById(locationId));
    }
}
