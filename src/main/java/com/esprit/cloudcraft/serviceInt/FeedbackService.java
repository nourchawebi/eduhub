package com.esprit.cloudcraft.serviceInt;

import com.esprit.cloudcraft.dto.RateAvg;
import com.esprit.cloudcraft.entities.Feedback;

import java.util.List;

public interface FeedbackService {
    Feedback addFeedback(Feedback feedback, Long idUser, Integer idJourney);
    Feedback updateFeedback(Feedback feedback, Long idUser, Integer idJourney);

    List<Feedback> getFeedbacksByJourney(Integer idJourney);

    Feedback getFeedback(Long idFeedback);
    Feedback getFeedbackByUserAndJourney(Long idUser, Integer idJourney);

    RateAvg getRateAvg(Long idUser);

}
