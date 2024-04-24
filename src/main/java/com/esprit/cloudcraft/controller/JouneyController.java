package com.esprit.cloudcraft.controller;

import com.esprit.cloudcraft.entities.Journey;
import com.esprit.cloudcraft.entities.User;
import com.esprit.cloudcraft.services.JourneyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@Controller
public class JouneyController {
    @Autowired
    private JourneyService journeyService;

    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping("journey")
    @ResponseBody
    public Journey addCar(@RequestBody Journey journey){

        System.out.println(new Date(0,0,0,5,5));
        return journeyService.addJourney(journey);
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
