package com.esprit.cloudcraft.services;

import com.esprit.cloudcraft.entities.Event;
import com.esprit.cloudcraft.Enum.Name;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface IEventService {
    public List<Event> getallEvents();



    public Boolean addEvent(Event newEvent, MultipartFile image) ;

    public Event createEvent(Event e);

    public Event updateEvent(long id, Event updatedEvent,MultipartFile picture);

    public void deleteEvent(long id);

    public Event findEventByCapacityAndIdEventt(long capacity, long idEvent);

    public List<Event> findEventsByClubTitleAndEventName(Name clubName, String eventTitle);

    public List<Event> findAllEventsByCapacityBetween(Integer startCapacity, Integer endCapacity);

    public List<Event> findAllByCapacity(long c);
    public Event findeventbyid(long id);
    public List<Event> findbyid(long id);
    //  public void participate(long eventId, long userId);
    public boolean IsparticipateUserInEvent(Long eventId, Long userId);
    public Event participateUserInEvent(Long eventId, Long userId);

    public Event cancelUserParticipation(Long eventId, Long userId);


    public Map<String, Long> countEventByMonth();
    public Map<String, Long> countEventsByMonth();
}