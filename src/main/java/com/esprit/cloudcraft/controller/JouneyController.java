package com.esprit.cloudcraft.controller;

import com.esprit.cloudcraft.entities.Journey;
import com.esprit.cloudcraft.entities.userEntities.User;
import com.esprit.cloudcraft.serviceInt.JourneyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Date;
import java.util.List;

@Controller
public class JouneyController {
    @Autowired
    private JourneyService journeyService;

    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping("journey")
    @ResponseBody
    public Journey addJourney(@RequestBody Journey journey, Principal connectedUser){
        User user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();
        return journeyService.addJourney(journey,user);
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("journey")
    @ResponseBody
    public List<Journey> getAllJourneys(){
        return journeyService.getAllJourneys();
    }

    @GetMapping("journey_by_motorized")
    @ResponseBody
    public List<Journey> getJourneysByMotorized(@RequestBody User motorized){
        return journeyService.getJourneysByMotorized(motorized);
    }


}
