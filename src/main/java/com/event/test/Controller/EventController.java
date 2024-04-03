package com.event.test.Controller;

import com.event.test.Entity.Event;
import com.event.test.Enum.Name;
import com.event.test.InterfaceService.IClubService;
import com.event.test.InterfaceService.IEventService;
import com.event.test.utils.QRCodeGenerator;
import com.google.zxing.WriterException;
import lombok.*;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/event")
public class EventController {
    private final   IEventService eventService;
    public EventController(IEventService eventService) {
        this.eventService = eventService;
    }
    @GetMapping("/getAll")
    public List<Event> getAllEvents(){
    return eventService.getallEvents();
};


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

    // ken capacite 5altet max maynajamch iparticipi
    @PostMapping("/{eventId}/participate/{userId}")
    public ResponseEntity<String> participateUserInEvent(@PathVariable Long eventId, @PathVariable Long userId) {
        try {
            eventService.participateUserInEvent(eventId, userId);
            return ResponseEntity.ok("User participated in the event successfully.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }







}

