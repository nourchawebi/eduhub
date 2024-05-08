package com.esprit.cloudcraft.controller;

import com.esprit.cloudcraft.entities.Journey;
import com.esprit.cloudcraft.entities.userEntities.User;
import com.esprit.cloudcraft.serviceInt.JourneyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.Month;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("journey/participated")
    @ResponseBody
    public List<Journey> getJourneysParticipated(Principal connectedUser){
        User user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();
        return journeyService.getJourneysParticipated(user.getId());
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("journey/{id}")
    @ResponseBody
    public Journey getJourney(@PathVariable("id") Integer id){
        return journeyService.getJourney(id);
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("journey/{id}/motorized")
    @ResponseBody
    public User getJourneyMotorized(@PathVariable("id") Integer id){
        return journeyService.getJourneyMotorized(id);
    }



    @GetMapping("journey_by_motorized")
    @ResponseBody
    public List<Journey> getJourneysByMotorized(@RequestBody User motorized){
        return journeyService.getJourneysByMotorized(motorized);
    }

    @GetMapping("journey/statistics")
    @ResponseBody
    public Map<String, Long> countJourneyByDay(){
        return journeyService.countJourneyByDay();
    }


}
