package com.event.test.Service;

import com.event.test.Entity.Event;
import com.event.test.Entity.User;
import com.event.test.Enum.Name;
import com.event.test.InterfaceService.IEventService;
import com.event.test.Repository.ClubRepository;
import com.event.test.Repository.EventRepository;
import com.event.test.Repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class EventService implements IEventService {
    private final EventRepository cc;

    private final UserRepository userRepository;


    public EventService(EventRepository cc, UserRepository userRepository) {
        this.cc = cc;
        this.userRepository = userRepository;
    }
    public List<Event> getallEvents(){
        return cc.findAll();
    };

    public Event createEvent(Event e){
        return cc.save(e);
    };
    public Event updateEvent(long id, Event updatedEvent){
        Optional<Event> up = cc.findById(id);
        if (up.isPresent()) {
            Event event = up.get();
            event.setCapacity(updatedEvent.getCapacity());
            event.setDetails(updatedEvent.getDetails());
            event.setDateBegin(updatedEvent.getDateBegin());
            event.setDateEnd(updatedEvent.getDateEnd());
            event.setDescription(updatedEvent.getDescription());
            event.setTitle(updatedEvent.getTitle());
            return cc.save(event);
        }
        else {
            return null;
        }
    };
    public void deleteEvent(long id){
         cc.deleteById(id);
    }
        public Event findEventByCapacityAndIdEventt(long capacity, long idEvent) {
           return cc.findEventByCapacityAndIdEvent(capacity , idEvent);
    }
    public List<Event> findEventsByClubTitleAndEventName(Name clubName, String eventTitle) {
        return cc.findEventsByClubTitleAndEventName(clubName, eventTitle);
    }

    public List<Event> findAllEventsByCapacityBetween(Integer startCapacity, Integer endCapacity) {
        return cc.findAllByCapacityBetween(startCapacity, endCapacity);
    }


    public List<Event> findAllByCapacity(long c){
         return  cc.findAllByCapacity(c);
    }
    @Override
    public Event findbyid(long id) {
        return cc.findById(id).get();
    }
    public Event participateUserInEvent(Long eventId, Long userId) {
        Event event = cc.findById(eventId).orElseThrow(() -> new RuntimeException("Event not found"));
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

        if (event.getUserSet().size() >= event.getCapacity()) {
            throw new RuntimeException("Event capacity has been reached. Cannot participate.");
        }

        event.getUserSet().add(user);
        return cc.save(event);
    }

}
