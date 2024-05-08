package com.esprit.cloudcraft.implement;

import com.esprit.cloudcraft.repository.LocationDao;
import com.esprit.cloudcraft.repository.userDao.UserRepository;
import com.esprit.cloudcraft.entities.Location;
import com.esprit.cloudcraft.serviceInt.LocationService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import com.esprit.cloudcraft.entities.userEntities.User;


import java.util.*;

@Service
public class LocationServiceImp implements LocationService {
    @Resource
    LocationDao locationDao;
    @Resource
    UserRepository userDao;

    @Override
    public User addLocation(Long userId, Location location) {
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
    public User updateLocation(Long userId, Location location) {
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
    public Location getUserLocation(Long id) {
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

    @Override
    public Map<String, Long> countUsersByLocation() {
        Long count;
        Map<String, Long> result = new HashMap<>();;
        List<User> users = userDao.findAll();
        for (String s:getLocationNames()){
            count=0L;
            for(User u: users){
                if(u.getLocation().getNameLocation()==s){
                    count++;
                }
            }
            result.put(s,count);
        }
        return result;
    }
}
