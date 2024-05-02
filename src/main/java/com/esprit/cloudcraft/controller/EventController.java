package com.esprit.cloudcraft.controller;

import com.esprit.cloudcraft.entities.Event;
import com.esprit.cloudcraft.Enum.Name;
import com.esprit.cloudcraft.entities.userEntities.User;
import com.esprit.cloudcraft.services.IEventService;
import com.esprit.cloudcraft.utils.QRCodeGenerator;
import com.google.zxing.WriterException;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.time.LocalDate;
import java.util.List;
@Controller
@RestController
@CrossOrigin(origins = {"http://localhost:4200", "http://localhost:62699"})
@RequestMapping("/event")
public class EventController {
    private final IEventService eventService;
    public EventController(IEventService eventService) {
        this.eventService = eventService;
    }
    @GetMapping("/getAll")
    public List<Event> getAllEvents(){
    return eventService.getallEvents();
};

    //    /////////////////////////////////// CRUD //////////////////////////////////////////



    @PostMapping(value = "/addEvent", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Object> addEvent(@RequestParam String title, @RequestParam LocalDate dateBegin, @RequestParam LocalDate dateEnd, @RequestParam String location, @RequestParam String details, @RequestParam String description, @RequestParam MultipartFile picture) {
//        System.out.println(picture);

        Event newEvent = Event.builder()
                .title(title)
                .dateBegin(dateBegin)
                .dateEnd(dateEnd)
                .location(location)
                .details(details)
                .description(description)
                .build();

        eventService.addEvent(newEvent, picture);
        return ResponseEntity.ok().build();
    }

















    @PostMapping("/create")
    public ResponseEntity<Event>   createEvent(@RequestBody Event e){
  Event e1 =eventService.createEvent(e);
  return ResponseEntity.ok(e1);
}



@PutMapping("/update/{id}")
    public Event updateEvent(@PathVariable long id  , @RequestBody Event e){
    return eventService.updateEvent(id,e);
}
@DeleteMapping("/delete/{id}")
    public void deleteEvent(@PathVariable long id){
     eventService.deleteEvent(id);
}

    //    /////////////////////////////////// recherche //////////////////////////////////////////


    @GetMapping("/find")
    public ResponseEntity<Event> findEventByCapacityAndIdEvent(
            @RequestParam long capacity,
            @RequestParam long idEvent) {
        Event event = eventService.findEventByCapacityAndIdEventt(capacity, idEvent);
        if (event != null) {
            return ResponseEntity.ok(event);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("/search")
    public ResponseEntity<List<Event>> getEventsByClubTitleAndEventName(
            @RequestParam Name clubName,
            @RequestParam String eventTitle) {
        List<Event> events = eventService.findEventsByClubTitleAndEventName(clubName, eventTitle);
        if (!events.isEmpty()) {
            return ResponseEntity.ok(events);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("/eventsByCapacityBetween")
    public ResponseEntity<List<Event>> getEventsByCapacityRange(
            @RequestParam Integer startCapacity,
            @RequestParam Integer endCapacity) {
        List<Event> events = eventService.findAllEventsByCapacityBetween(startCapacity, endCapacity);
        if (!events.isEmpty()) {
            return ResponseEntity.ok(events);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/eventsByCapacity")
    public List<Event> getEventsByCapacityRange(
            @RequestParam long capacity) {
    return  eventService.findAllByCapacity(capacity);
    }


// /////////////////////////////// participation et annulation de participation //////////////////////////////////////////

    @PostMapping("/{eventId}/participate")
    public ResponseEntity<String> participateUserInEvent(@PathVariable Long eventId, Principal connectedUser) {
        try {
            var user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();
            eventService.participateUserInEvent(eventId, user.getId());
            return ResponseEntity.ok("User participated in the event successfully.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

    @PostMapping("/{eventId}/cancelParticipation/{userId}")
    public ResponseEntity<String> cancelUserParticipation(@PathVariable Long eventId, @PathVariable Long userId) {
        try {
            eventService.cancelUserParticipation(eventId, userId);
            return ResponseEntity.ok("User participation cancelled successfully.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }



    ///////////////////////// hedhi ki n'executeha fi oossier yetsabou les QRCODES mtaa events
    @GetMapping("/getEventQRcode")
    public List<Event> getAllEventsQR() throws IOException,  WriterException {
        List<Event> events = eventService.getallEvents();
        if (!events.isEmpty()) {
            for (Event event : events){
                QRCodeGenerator.generateQRCode(event);
            }
        }
        return eventService.getallEvents();
    }


}

