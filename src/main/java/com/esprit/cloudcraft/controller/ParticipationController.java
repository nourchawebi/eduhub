package com.esprit.cloudcraft.controller;

import com.esprit.cloudcraft.entities.Journey;
import com.esprit.cloudcraft.entities.Participation;
import com.esprit.cloudcraft.entities.userEntities.User;
import com.esprit.cloudcraft.serviceInt.ParticipationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
public class ParticipationController {
    @Autowired
    ParticipationService participationService;

    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping("participation/{idJourney}")
    @ResponseBody
    public Participation addParticipation(@PathVariable Integer idJourney, Principal connectedUser){
        User user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();
        return participationService.addParticipation(user.getId(),idJourney);
    }

/*    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("participation/journey/{idJourney}")
    @ResponseBody
    public List<Participation> getJourneyParticipations(@PathVariable Integer idJourney){
        return participationService.getJourneyParticipations(idJourney);
    }*/

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("participation/journey/{idJourney}")
    @ResponseBody
    public Boolean checkParticipation(@PathVariable Integer idJourney,Principal connectedUser){
        User user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();
        return participationService.checkParticipation(idJourney,user.getId());
    }

/*    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("participation/carpooled/{idCarpooled}")
    @ResponseBody
    public List<Participation> getCarpoolerParticipations(@PathVariable Integer idCarpooled,Principal connectedUser){
        User user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();
        return participationService.getCarpoolerParticipations(user.getId());
    }*/

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("carpooled/{idCarpooled}/journeys")
    @ResponseBody
    public List<Journey> getCarpoolerJouneys(@PathVariable Integer idCarpooled, Principal connectedUser){
        User user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();
        return participationService.getCarpooledJourneys(user.getId());
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("participation/carpooled/{idJourney}")
    @ResponseBody
    public List<User> getJourneyCarpooled(@PathVariable Integer idJourney){
        return participationService.getJourneyCarpooled(idJourney);
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @DeleteMapping("participation/{idJourney}")
    @ResponseBody
    public void deleteParticipation(@PathVariable Integer idJourney, Principal connectedUser){
        User user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();
        participationService.removeParticipation(user.getId(),idJourney);
    }

}