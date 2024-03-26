package com.example.carpooling.services;

import com.example.carpooling.entities.Journey;
import com.example.carpooling.entities.Participation;

public interface ParticipationService {

    Participation addParticipation(Integer idCarpooled, Journey journey);

}
