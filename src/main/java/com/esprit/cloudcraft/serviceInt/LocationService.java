package com.esprit.cloudcraft.serviceInt;

import com.esprit.cloudcraft.entities.Location;
import com.esprit.cloudcraft.entities.userEntities.User;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface LocationService {
    User addLocation(Long userId, Location location);

    User updateLocation(Long userId, Location location);

    List<Location> getUsersLocation();
    void deleteLocation(Integer locationId);

    Location getUserLocation(Long id);

    Set<String> getLocationNames();

    Map<String,Long> countUsersByLocation();


    }
