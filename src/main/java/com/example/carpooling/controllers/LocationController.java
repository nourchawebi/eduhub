package com.example.carpooling.controllers;

import com.example.carpooling.entities.Location;
import com.example.carpooling.entities.User;
import com.example.carpooling.services.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class LocationController {
    @Autowired
    LocationService locationService;
    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping("location/{id}")
    @ResponseBody
    public User addLocation(@PathVariable("id") Integer id, @RequestBody Location location){
        return locationService.setLocation(id,location);
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

}
