package com.esprit.cloudcraft.implement;

import com.esprit.cloudcraft.entities.Day;
import com.esprit.cloudcraft.entities.userEntities.User;
import com.esprit.cloudcraft.repository.JourneyDao;
import com.esprit.cloudcraft.repository.userDao.UserRepository;
import com.esprit.cloudcraft.entities.Journey;
import com.esprit.cloudcraft.serviceInt.JourneyService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class journeyServiceImp implements JourneyService {
    @Resource
    JourneyDao journeyDao;
    @Resource
    UserRepository userDao;

    @Override
    public Journey addJourney(Journey journey, User user) {
        journey.setMotorized(userDao.getReferenceById(user.getId()));
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
    public Journey getJourney(Integer journeyId) {
        if (journeyDao.findById(journeyId).isPresent())
            return journeyDao.findById(journeyId).get();
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

    @Override
    public Map<String, Long> countJourneyByDay() {
        Long count;
        Map<String, Long> result = new HashMap<>();;
        List<Journey> journeys = journeyDao.findAll();
        for (Day day: Day.values()){
            count=0L;
            for(Journey journey: journeys){
                if(journey.getDay().getDay()==day.ordinal()){
                    count++;
                }
            }
            result.put(day.toString(),count);
        }
        return result;
    }

    @Override
    public User getJourneyMotorized(Integer id) {
        if (journeyDao.findById(id).isPresent())
            return journeyDao.findById(id).get().getMotorized();
        return null;
    }

    @Override
    public List<Journey> getJourneysParticipated(Long id) {
        return journeyDao.getJourneysParticipated(id);
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
