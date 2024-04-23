package com.example.carpooling.servicesImp;

import com.example.carpooling.daos.LocationDao;
import com.example.carpooling.daos.UserDao;
import com.example.carpooling.entities.Location;
import com.example.carpooling.entities.User;
import com.example.carpooling.services.LocationService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
                    {
                        locationDao.save(location);
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
            {
                User user = userDao.getReferenceById(userId);
                location.setLocationId(user.getLocation().getLocationId());
                locationDao.save(location);
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

    @Override
    public Location getUserLocation(Integer id) {
        if(userDao.existsById(id))
            return userDao.getReferenceById(id).getLocation();
        return null;
    }

    @Override
    public Set<String> getLocationNames() {
        Set<String> names = new HashSet<String>();
        List<Location> locations = locationDao.findAll();
        for (Location loc : locations)
            if(loc.getNameLocation()!=null && !loc.getNameLocation().isEmpty())
                names.add(loc.getNameLocation());
        return names;
    }
}
