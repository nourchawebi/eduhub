package com.esprit.cloudcraft.services;

import com.esprit.cloudcraft.entities.Location;
import com.esprit.cloudcraft.entities.User;

import java.util.List;
import java.util.Set;

public interface LocationService {
    User addLocation(Integer userId, Location location);

    User updateLocation(Integer userId, Location location);

    List<Location> getUsersLocation();
    void deleteLocation(Integer locationId);

    Location getUserLocation(Integer id);

    Set<String> getLocationNames();

}
