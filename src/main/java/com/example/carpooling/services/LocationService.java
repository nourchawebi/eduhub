package com.example.carpooling.services;

import com.example.carpooling.entities.Location;
import com.example.carpooling.entities.User;

import java.util.List;

public interface LocationService {
    User addLocation(Integer userId, Location location);
    User updateLocation(Integer userId, Location location);

    List<Location> getUsersLocation();
    void deleteLocation(Integer locationId);

}
