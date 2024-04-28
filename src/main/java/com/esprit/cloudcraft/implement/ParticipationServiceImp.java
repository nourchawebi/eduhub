package com.esprit.cloudcraft.implement;

import com.esprit.cloudcraft.repository.JourneyDao;
import com.esprit.cloudcraft.repository.ParticipationDao;
import com.esprit.cloudcraft.repository.userDao.UserRepository;
import com.esprit.cloudcraft.entities.Journey;
import com.esprit.cloudcraft.entities.Participation;
import com.esprit.cloudcraft.entities.userEntities.User;
import com.esprit.cloudcraft.serviceInt.ParticipationService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ParticipationServiceImp implements ParticipationService {
    @Resource
    ParticipationDao participationDao;
    @Resource
    UserRepository userDao;
    @Resource
    JourneyDao journeyDao;

    @Override
    public Participation addParticipation(Long idCarpooled, Integer idJourney) {
        if(idCarpooled!=null&&idJourney!=null)
            if(userDao.existsById(idCarpooled)&&journeyDao.existsById(idJourney)){
                User carpooled = userDao.getReferenceById(idCarpooled);
                Journey journey = journeyDao.getReferenceById(idJourney);
                if(!participationDao.existsParticipationByCarpooledAndJourney(carpooled,journey)){
                    Participation participation = new Participation();
                    participation.setCarpooled(carpooled);
                    participation.setJourney(journey);
                    participationDao.save(participation);
                    journey.getParticipations().add(participation);
                    journey.setAvailablePlaces(journey.getAvailablePlaces()-1);
                    journeyDao.save(journey);
                    carpooled.getParticipations().add(participation);
                    userDao.save(carpooled);
                    return participation;
                }
            }
        return null;
    }

    @Override
    public List<Participation> getJourneyParticipations(Integer idJourney) {
        if(idJourney!=null)
            if(journeyDao.existsById(idJourney))
                return participationDao.findAllByJourney(journeyDao.getReferenceById(idJourney));
        return null;
    }

    @Override
    public List<Participation> getCarpoolerParticipations(Long idCarpooled) {
        if (idCarpooled!=null)
            if (userDao.existsById(idCarpooled))
                return participationDao.findAllByCarpooled(userDao.getReferenceById(idCarpooled));
        return null;
    }

    @Override
    public List<User> getJourneyCarpooled(Integer idJourney) {
        if(idJourney!=null)
            if(journeyDao.existsById(idJourney)){
                Journey journey = journeyDao.getReferenceById(idJourney);
                List<Participation> participations = journey.getParticipations();
                List<User> users = userDao.getUserByParticipationsIn(participations);
                return users;
            }
        return null;
    }

    @Override
    public List<Journey> getCarpooledJourneys(Long idCarpooled) {
        if(idCarpooled!=null)
            if(userDao.existsById(idCarpooled)){
                System.out.println(idCarpooled);
                User user = userDao.getReferenceById(idCarpooled);
                List<Participation> participations = user.getParticipations();
                List<Journey> journeys = journeyDao.getJourneysByParticipationsIn(participations);
                return journeys;
            }
        return null;
    }

    @Override
    public void removeParticipation(Integer idParticipation) {
        if (idParticipation!=null)
            if(participationDao.existsById(idParticipation))
                participationDao.deleteById(idParticipation);
    }

    @Override
    public Boolean checkParticipation(Integer idJourney,Long idCarpooler) {
        if(idJourney!=null)
            if(participationDao.existsById(idJourney))
                return participationDao.existsParticipationByCarpooledAndJourney(userDao.getReferenceById(idCarpooler),journeyDao.getReferenceById(idJourney));
        return null;
    }
}
