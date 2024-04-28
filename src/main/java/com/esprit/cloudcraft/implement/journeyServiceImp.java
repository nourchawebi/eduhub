package com.esprit.cloudcraft.implement;

import com.esprit.cloudcraft.entities.userEntities.User;
import com.esprit.cloudcraft.repository.JourneyDao;
import com.esprit.cloudcraft.repository.userDao.UserRepository;
import com.esprit.cloudcraft.entities.Journey;
import com.esprit.cloudcraft.serviceInt.JourneyService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class journeyServiceImp implements JourneyService {
    @Resource
    JourneyDao journeyDao;
    @Resource
    UserRepository userDao;

    @Override
    public Journey addJourney(Journey journey, User user) {
        journey.setMotorized(user);
        return journeyDao.save(journey);
    }

    @Override
    public Journey modifyJourney(Integer journeyId, Journey journey) {
        if(journeyId!=null)
            if(journeyDao.existsById(journeyId)){
                journey.setJourneyId(journeyId);
                return journeyDao.save(journey);
            }
        return null;
    }

    @Override
    public List<Journey> getAllJourneys() {
        return journeyDao.findAll();
    }

    @Override
    public void deleteJourneyById(Integer journeyId) {
        if(journeyDao.existsById(journeyId))
            journeyDao.deleteById(journeyId);
    }


    @Override
    public List<Journey> getJourneysByMotorized(User user) {
        if(!journeyDao.getJourneysByMotorized(user).isEmpty())
            return journeyDao.getJourneysByMotorized(user);
        return new ArrayList<>();
    }

    /*@Override
    public Journey addPassengerToJourney(Integer userId, Integer journeyId) {
        if(userId != null && journeyId !=null)
            if(userDao.existsById(userId) && journeyDao.existsById(journeyId)){
                Journey journey = journeyDao.getReferenceById(journeyId);
                List<User> passengers;
                if(journey.getPassengers().isEmpty()){
                    passengers = new ArrayList<>();
                    passengers.add(userDao.getReferenceById(userId));
                }
                else {
                    passengers = journey.getPassengers();
                    passengers.add(userDao.getReferenceById(userId));
                }
                journey.setPassengers(passengers);
                return journeyDao.save(journey);
            }
        return null;
    }*/


}
