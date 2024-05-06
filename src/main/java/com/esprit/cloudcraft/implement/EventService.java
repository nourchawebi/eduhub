package com.esprit.cloudcraft.implement;

import com.esprit.cloudcraft.entities.Event;
import com.esprit.cloudcraft.entities.userEntities.User;
import com.esprit.cloudcraft.Enum.Name;
import com.esprit.cloudcraft.repository.userDao.UserRepository;
import com.esprit.cloudcraft.services.EmailWithAttachmentService;
import com.esprit.cloudcraft.services.FileStorageService;
import com.esprit.cloudcraft.services.IEventService;
import com.esprit.cloudcraft.repository.EventRepository;

import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.Month;
import java.util.*;

@Service
public class EventService implements IEventService {
    @Resource
    private EmailWithAttachmentService sendMail;
    @Resource
    private FileStorageService fileStorageService;
    private final EventRepository cc;

    private final UserRepository userRepository;


    public EventService(EventRepository cc, UserRepository userRepository) {
        this.cc = cc;
        this.userRepository = userRepository;
    }

    //    /////////////////////////////////// CRUD //////////////////////////////////////////

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
            Event savedEvent = cc.save(event);
            // Customize the email message
            String emailSubject = "Event Created!";
            String emailBody = "Hello,\n\n"
                    + "We are excited to inform you that the event titled \"" + newEvent.getTitle() + "\" has been successfully created!\n"
                    + "Event details:\n"
                    + "- Date: " + newEvent.getDateBegin() + " to " + newEvent.getDateEnd() + "\n"
                    + "- Location: " + newEvent.getLocation() + "\n"
                    + "- Description: " + newEvent.getDescription() + "\n"
                    + "- Capacity: " + newEvent.getCapacity() + "\n\n"
                    + "Thank you for organizing this event. Let's make it a memorable one! 😊";

            sendMail.SendEmail("nourelhoudachawebi@gmail.com", emailSubject, emailBody);
            result = Boolean.TRUE;
        }
        return result;
    }



    public Event createEvent(Event e){

        return cc.save(e);
    };
    public Event updateEvent(long id, Event updatedEvent,MultipartFile picture){
        Optional<Event> up = cc.findById(id);
        if (up.isPresent()) {
            Event event = up.get();
            event.setCapacity(updatedEvent.getCapacity());
            event.setDetails(updatedEvent.getDetails());
            event.setDateBegin(updatedEvent.getDateBegin());
            event.setDateEnd(updatedEvent.getDateEnd());
            event.setDescription(updatedEvent.getDescription());
            event.setTitle(updatedEvent.getTitle());
            if (picture != null){
                event.setPicture(fileStorageService.saveImage(picture));
            }
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
    public List<Event> getallEvents(){
        return cc.findAll();
    };

    public List<Event> findbyid(long id) {
        Optional<Event> optionalEvent = cc.findById(id);
        return optionalEvent.map(Collections::singletonList).orElse(Collections.emptyList());
    }


    public Event findeventbyid(long id) {

        return cc.findById(id).get();
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




// //    /////////////////////////////////// participation et annulation de participation //////////////////////////////////////////

    public boolean IsparticipateUserInEvent(Long eventId, Long userId) {
        Event event = cc.findById(eventId).orElseThrow(() -> new RuntimeException("Event not found"));
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

        // Check if the user is already participating in the event
        if (event.getUserSet().contains(user)) {
            throw new RuntimeException("User is already participating in the event.");
        }

        if (event.getUserSet().size() >= event.getCapacity()) {
            throw new RuntimeException("Event capacity has been reached. Cannot participate.");
        }

        event.getUserSet().add(user);
        cc.save(event);

        return true; // User participated successfully
    }



    public Event participateUserInEvent(Long eventId, Long userId) {
        Event event = cc.findById(eventId).orElseThrow(() -> new RuntimeException("Event not found"));
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

        if (event.getUserSet().size() >= event.getCapacity()) {
            throw new RuntimeException("Event capacity has been reached. Cannot participate.");
        }

        event.getUserSet().add(user);
        // Customize the email message
        String emailSubject = "Participation Confirmation";
        String emailBody = "Hello,\n\n"
                + "Thank you for participating in the event titled \"" + event.getTitle() + "\"!\n"
                + "We look forward to seeing you there.\n\n"
                + "Best regards,\n"
                + "Event Organizer";

        sendMail.SendEmail("nourelhoudachawebi@gmail.com", emailSubject, emailBody);
        return cc.save(event);
    }


    public Event cancelUserParticipation(Long eventId, Long userId) {
        Event event = cc.findById(eventId).orElseThrow(() -> new RuntimeException("Event not found"));
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

        if (!event.getUserSet().contains(user)) {
            throw new RuntimeException("User is not participating in this event.");
        }

        event.getUserSet().remove(user);
        // Customize the email message
        String emailSubject = "Participation Canceled";
        String emailBody = "Hello,\n\n"
                + "We're sorry to see that you've canceled your participation in the event titled \"" + event.getTitle() + "\".\n"
                + "If you change your mind, feel free to join us again in the future!\n\n"
                + "Best regards,\n"
                + "Event Organizer";

        sendMail.SendEmail("nourelhoudachawebi@gmail.com", emailSubject, emailBody);
        return cc.save(event);
    }
    @Override
    public Map<String, Long> countEventByMonth() {
//        List<String> months =[]
//        Long count;
//        Map<String, Long> result = new HashMap<>();;
//        List<Event> events = cc.findAll();
//        for (Month month: Month.values()){
//            count=0L;
//            for(Journey journey: journeys){
//                if(journey.getDay()==day){
//                    count++;
//                }
//            }
//            result.put(day.toString(),count);
//        }
        return null;
    }

    public Map<String, Long> countEventsByMonth() {
        List<Event> allEvents = cc.findAll();
        Map<String, Long> eventsByMonth = new HashMap<>();

        for (Event event : allEvents) {
            // Convert java.sql.Date to LocalDate
            LocalDate localDate = event.getDateBegin().atStartOfDay().toLocalDate();

            // Extract the month from the LocalDate
            int monthNumber = localDate.getMonthValue();
            String monthName = Month.of(monthNumber).name();

            // Increment the count for the corresponding month
            eventsByMonth.put(monthName, eventsByMonth.getOrDefault(monthName, 0L) + 1);
        }

        return eventsByMonth;
    }


}
