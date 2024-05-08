package com.esprit.cloudcraft.controller;

import com.esprit.cloudcraft.entities.Location;
import com.esprit.cloudcraft.entities.userEntities.User;
import com.esprit.cloudcraft.serviceInt.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Controller
public class LocationController {
    @Autowired
    LocationService locationService;
    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping("location/{id}")
    @ResponseBody
    public User addLocation(@PathVariable("id") Long id, @RequestBody Location location, Principal connectedUser){
        User user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();
        System.out.println("ok "+user.getEmail());
        return locationService.addLocation(user.getId(),location);

    }

    @CrossOrigin(origins = "http://localhost:4200")
    @PutMapping("location/{id}")
    @ResponseBody
    public User updateLocation(@PathVariable("id") Long id, @RequestBody Location location, Principal connectedUser){
        User user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();
        return locationService.updateLocation(user.getId(),location);
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("location/{id}")
    @ResponseBody
    public Location getUserLocation(@PathVariable("id") Long id, Principal connectedUser){
        User user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();
        return locationService.getUserLocation(user.getId());
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

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("location/statistics")
    @ResponseBody
    public Map<String, Long> countUsersByLocation(){
        return locationService.countUsersByLocation();
    }


}