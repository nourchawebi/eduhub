package com.esprit.cloudcraft.implement;

import com.esprit.cloudcraft.controller.userController.UserController;
import com.esprit.cloudcraft.dto.RateAvg;
import com.esprit.cloudcraft.entities.Feedback;
import com.esprit.cloudcraft.entities.Journey;
import com.esprit.cloudcraft.entities.userEntities.User;
import com.esprit.cloudcraft.repository.FeedbackDao;
import com.esprit.cloudcraft.repository.JourneyDao;
import com.esprit.cloudcraft.repository.userDao.UserRepository;
import com.esprit.cloudcraft.serviceInt.FeedbackService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FeedbackServiceImp implements FeedbackService {

    @Resource
    private FeedbackDao feedbackDao;
    @Resource
    private JourneyDao journeyDao;
    @Resource
    private UserRepository userRepository;

    @Override
    public Feedback addFeedback(Feedback feedback, Long idUser, Integer idJourney) {
        if (idUser!=null&&idJourney!=null&&feedback!=null){
            feedback.setUser(userRepository.getReferenceById(idUser));
            feedback.setJourney(journeyDao.getReferenceById(idJourney));
            feedbackDao.save(feedback);
        }
        return null;
    }

    @Override
    public Feedback updateFeedback(Feedback feedback, Long idUser, Integer idJourney) {
        if (idUser!=null&&idJourney!=null&&feedback!=null){
            User user = userRepository.getReferenceById(idUser);
            Journey journey = journeyDao.findById(idJourney).get();
            Feedback feedback1 = feedbackDao.findByUserAndJourney(user,journey);
            feedback1.setJourney(journey);
            feedback1.setUser(user);
            feedback1.setComment(feedback.getComment());
            feedback1.setRating(feedback.getRating());
            feedbackDao.save(feedback);
        }
        return null;
    }

    @Override
    public List<Feedback> getFeedbacksByJourney(Integer idJourney) {
        if(idJourney != null)
            if (journeyDao.findById(idJourney).isPresent())
                return feedbackDao.findByJourney(journeyDao.findById(idJourney).get());
        return null;
    }

    @Override
    public Feedback getFeedback(Long idFeedback) {
        if (feedbackDao.findById(idFeedback).isPresent())
            return feedbackDao.findById(idFeedback).get();
        return null;
    }

    @Override
    public Feedback getFeedbackByUserAndJourney(Long idUser, Integer idJourney) {
        if(userRepository.findById(idUser).isPresent()&&journeyDao.findById(idJourney).isPresent())
            return feedbackDao.findByUserAndJourney(userRepository.findById(idUser).get(),journeyDao.findById(idJourney).get());
        return null;
    }

    @Override
    public RateAvg getRateAvg(Long idUser) {
        RateAvg rateAvg =  new RateAvg();
        rateAvg.setRateAvg(feedbackDao.getAvgRating(idUser));
        rateAvg.setRateNumber(feedbackDao.getcountRating(idUser));
        return rateAvg;
    }


}
