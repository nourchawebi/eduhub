package com.esprit.cloudcraft.controller;

import com.esprit.cloudcraft.entities.Journey;
import com.esprit.cloudcraft.entities.Participation;
import com.esprit.cloudcraft.entities.User;
import com.esprit.cloudcraft.services.ParticipationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class ParticipationController {
    @Autowired
    ParticipationService participationService;

    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping("participation/{idCarpooled}/{idJourney}")
    @ResponseBody
    public Participation addParticipation(@PathVariable Integer idCarpooled,@PathVariable Integer idJourney){
        return participationService.addParticipation(idCarpooled,idJourney);
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
    public Boolean checkParticipation(@PathVariable Integer idJourney){

        return participationService.checkParticipation(idJourney);
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("participation/carpooled/{idCarpooled}")
    @ResponseBody
    public List<Participation> getCarpoolerParticipations(@PathVariable Integer idCarpooled){
        return participationService.getCarpoolerParticipations(idCarpooled);
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("carpooled/{idCarpooled}/journeys")
    @ResponseBody
    public List<Journey> getCarpoolerJouneys(@PathVariable Integer idCarpooled){
        return participationService.getCarpooledJourneys(idCarpooled);
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("journey/{idJourney}/carpooled")
    @ResponseBody
    public List<User> getJourneyCarpooled(@PathVariable Integer idJourney){
        return participationService.getJourneyCarpooled(idJourney);
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("participation/participation/{idParticipation}")
    @ResponseBody
    public void removeParticipation(@PathVariable Integer idParticipation){
        participationService.removeParticipation(idParticipation);
    }


}
