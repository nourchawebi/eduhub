package com.esprit.cloudcraft.controller;

import com.esprit.cloudcraft.entities.Location;
import com.esprit.cloudcraft.entities.User;
import com.esprit.cloudcraft.services.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@Controller
public class LocationController {
    @Autowired
    LocationService locationService;
    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping("location/{id}")
    @ResponseBody
    public User addLocation(@PathVariable("id") Integer id, @RequestBody Location location){
        return locationService.addLocation(id,location);
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @PutMapping("location/{id}")
    @ResponseBody
    public User updateLocation(@PathVariable("id") Integer id, @RequestBody Location location){
        return locationService.updateLocation(id,location);
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("location/{id}")
    @ResponseBody
    public Location getUserLocation(@PathVariable("id") Integer id){
        return locationService.getUserLocation(id);
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("location")
    @ResponseBody
    public List<Location> getAllLocations(){
        return locationService.getUsersLocation();
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @DeleteMapping("location/{id}")
    @ResponseBody
    public void deleteLocation(@PathVariable("id") Integer id){
        locationService.deleteLocation(id);
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("location/names")
    @ResponseBody
    public Set<String> getLocationNames(){
        return locationService.getLocationNames();
    }

}
