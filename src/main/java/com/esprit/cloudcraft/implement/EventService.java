package com.esprit.cloudcraft.implement;

import com.esprit.cloudcraft.entities.Event;
import com.esprit.cloudcraft.entities.userEntities.User;
import com.esprit.cloudcraft.Enum.Name;
import com.esprit.cloudcraft.repository.userDao.UserRepository;
import com.esprit.cloudcraft.services.FileStorageService;
import com.esprit.cloudcraft.services.IEventService;
import com.esprit.cloudcraft.repository.EventRepository;

import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Service
public class EventService implements IEventService {
    @Resource
    private FileStorageService fileStorageService;
    private final EventRepository cc;

    private final UserRepository userRepository;


    public EventService(EventRepository cc, UserRepository userRepository) {
        this.cc = cc;
        this.userRepository = userRepository;
    }
    public List<Event> getallEvents(){
        return cc.findAll();
    };






    @Override
    public Boolean addEvent(Event newEvent, MultipartFile image) {
        boolean result = Boolean.FALSE;
            if (newEvent != null) {
                Event event = new Event();
                event.setTitle(newEvent.getTitle());
                event.setDateBegin(newEvent.getDateBegin());
                event.setDateEnd(newEvent.getDateEnd());
                event.setLocation(newEvent.getLocation());
                event.setDetails(newEvent.getDetails());
                event.setDescription(newEvent.getDescription());
                event.setPicture(fileStorageService.saveImage(image));
                event.setCapacity(newEvent.getCapacity());

                // Assuming you have an EventDao or repository for saving events
                Event savedEvent = cc.save(event);

                // Adding the saved event to the category's events

                result = Boolean.TRUE;
            }
        return result;
    }





    //    /////////////////////////////////// CRUD //////////////////////////////////////////
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

    //    /////////////////////////////////// recherche //////////////////////////////////////////
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


// //    /////////////////////////////////// participation et annulation de participation //////////////////////////////////////////
    public Event participateUserInEvent(Long eventId, Long userId) {
        Event event = cc.findById(eventId).orElseThrow(() -> new RuntimeException("Event not found"));
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

        if (event.getUserSet().size() >= event.getCapacity()) {
            throw new RuntimeException("Event capacity has been reached. Cannot participate.");
        }

        event.getUserSet().add(user);
        return cc.save(event);
    }


    public Event cancelUserParticipation(Long eventId, Long userId) {
        Event event = cc.findById(eventId).orElseThrow(() -> new RuntimeException("Event not found"));
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

        if (!event.getUserSet().contains(user)) {
            throw new RuntimeException("User is not participating in this event.");
        }

        event.getUserSet().remove(user);
        return cc.save(event);
    }


}
