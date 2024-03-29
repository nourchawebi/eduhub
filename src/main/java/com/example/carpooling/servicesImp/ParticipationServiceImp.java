package com.example.carpooling.servicesImp;

import com.example.carpooling.daos.JourneyDao;
import com.example.carpooling.daos.ParticipationDao;
import com.example.carpooling.daos.UserDao;
import com.example.carpooling.entities.Journey;
import com.example.carpooling.entities.Participation;
import com.example.carpooling.services.ParticipationService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ParticipationServiceImp implements ParticipationService {
    @Resource
    ParticipationDao participationDao;
    @Resource
    UserDao userDao;
    @Resource
    JourneyDao journeyDao;

    @Override
    public Participation addParticipation(Integer idCarpooled, Integer idJourney) {
        if(idCarpooled!=null&&idJourney!=null)
            if(userDao.existsById(idCarpooled)&&journeyDao.existsById(idJourney)){
                Participation participation = new Participation();
                participation.setCarpooled(userDao.getReferenceById(idCarpooled));
                participation.setJourney(journeyDao.getReferenceById(idJourney));
                return participationDao.save(participation);
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
    public List<Participation> getCarpoolerParticipations(Integer idCarpooled) {
        if (idCarpooled!=null)
            if (userDao.existsById(idCarpooled))
                return participationDao.findAllByCarpooled(userDao.getReferenceById(idCarpooled));
        return null;
    }

    @Override
    public void removeParticipation(Integer idParticipation) {
        if (idParticipation!=null)
            if(participationDao.existsById(idParticipation))
                participationDao.deleteById(idParticipation);
    }
}
