package com.esprit.cloudcraft.controller;

import com.esprit.cloudcraft.dto.RateAvg;
import com.esprit.cloudcraft.entities.Feedback;
import com.esprit.cloudcraft.entities.Journey;
import com.esprit.cloudcraft.entities.userEntities.User;
import com.esprit.cloudcraft.serviceInt.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Map;

@Controller
public class FeedBackController {

    @Autowired
    private FeedbackService feedbackService;

    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping("feedback/{idJourney}")
    @ResponseBody
    public Feedback addJourney(@PathVariable("idJourney") Integer idJourney,@RequestBody Feedback feedback, Principal connectedUser){
        User user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();
        return feedbackService.addFeedback(feedback,user.getId(),idJourney);
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @PutMapping("feedback/{idJourney}")
    @ResponseBody
    public Feedback updateFeedback(@PathVariable("idJourney") Integer idJourney,@RequestBody Feedback feedback, Principal connectedUser){
        User user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();
        return feedbackService.updateFeedback(feedback,user.getId(),idJourney);
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("feedback/{idJourney}")
    @ResponseBody
    public List<Feedback> getFeedbacksByJourney(@PathVariable("idJourney") Integer idJourney){
        return feedbackService.getFeedbacksByJourney(idJourney);
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("feedback/user/{idJourney}")
    @ResponseBody
    public Feedback getFeedbackByUserAndJourney(@PathVariable("idJourney") Integer idJourney,Principal connectedUser){
        User user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();
        return feedbackService.getFeedbackByUserAndJourney(user.getId(),idJourney);
    }

    @CrossOrigin(origins = "http://localhost:4200")
        @GetMapping("feedback/useravg/{idUser}")
    @ResponseBody
    public RateAvg getFeedbackAvg(@PathVariable("idUser") Long idUser){
        return feedbackService.getRateAvg(idUser);
    }




    /*@GetMapping("feedback/statistics")
    @ResponseBody
    public Feedback getFeedback(){
        return feedbackService.getFeedback(1L);
    }*/

}
