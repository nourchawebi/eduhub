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
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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
                User carpooled = userDao.findById(idCarpooled).get();
                Journey journey = journeyDao.findById(idJourney).get();
                if(!participationDao.existsParticipationByCarpooledAndJourney(carpooled,journey) && journey.getAvailablePlaces()>0){
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
            if(journeyDao.findById(idJourney).isPresent()){
                Journey journey = journeyDao.findById(idJourney).get();
                List<Participation> participations = journey.getParticipations();
                return userDao.getUserByParticipationsIn(participations);
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
                List<Journey> journeys = new ArrayList<>();
                for(Participation participation:participations){
                    journeys.add(journeyDao.getJourneyByParticipationsContains(participation));
                }
                return journeys;
            }
        return null;
    }
    @Override
    public void removeParticipation(Long idCarpooled, Integer idJourney) {
        System.out.println("ok  "+idCarpooled);

        if (idJourney!=null && idCarpooled!=null)
            if( journeyDao.findById(idJourney).isPresent() && userDao.findById(idCarpooled).isPresent()){
                Participation participation = participationDao.findByCarpooledAndJourney(userDao.findById(idCarpooled).get(),journeyDao.findById(idJourney).get());

                User user = userDao.findById(idCarpooled).get();
                List<Participation> participations = user.getParticipations();
                participations.remove(participation);
                user.setParticipations(participations);
                userDao.save(user);

                Journey journey = journeyDao.findById(idJourney).get();
                participations = journey.getParticipations();
                participations.remove(participation);
                journey.setParticipations(participations);
                journey.setAvailablePlaces(journey.getAvailablePlaces()+    1);
                journeyDao.save(journey);

                if (participation!=null)
                    participationDao.delete(participation);
            }
    }

    @Override
    public Boolean checkParticipation(Integer idJourney,Long idCarpooler) {
        if(idJourney!=null)
            if(journeyDao.existsById(idJourney))
                return participationDao.existsParticipationByCarpooledAndJourney(userDao.getReferenceById(idCarpooler),journeyDao.getReferenceById(idJourney));
        return null;
    }
}
