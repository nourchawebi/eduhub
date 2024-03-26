package com.example.carpooling.controllers;

import com.example.carpooling.entities.Car;
import com.example.carpooling.entities.Journey;
import com.example.carpooling.entities.User;
import com.example.carpooling.services.JourneyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
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
