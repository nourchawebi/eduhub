package com.event.test.InterfaceService;

import com.event.test.Entity.Event;
import com.event.test.Enum.Name;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IEventService {
    public List<Event> getallEvents();



    public Boolean addEvent(Event newEvent, MultipartFile image) ;

        public Event createEvent(Event e);

    public Event updateEvent(long id, Event updatedEvent);

    public void deleteEvent(long id);

    public Event findEventByCapacityAndIdEventt(long capacity, long idEvent);

    public List<Event> findEventsByClubTitleAndEventName(Name clubName, String eventTitle);

    public List<Event> findAllEventsByCapacityBetween(Integer startCapacity, Integer endCapacity);

    public List<Event> findAllByCapacity(long c);

    public Event findbyid(long id);
    //  public void participate(long eventId, long userId);

    public Event participateUserInEvent(Long eventId, Long userId);

    public Event cancelUserParticipation(Long eventId, Long userId);
}
