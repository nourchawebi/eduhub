package com.esprit.cloudcraft.controller;

import com.esprit.cloudcraft.dto.EventMapper;
import com.esprit.cloudcraft.dto.EventPayLoad;
import com.esprit.cloudcraft.entities.Event;
import com.esprit.cloudcraft.Enum.Name;
import com.esprit.cloudcraft.entities.userEntities.User;
import com.esprit.cloudcraft.services.FileStorageService;
import com.esprit.cloudcraft.services.IEventService;
import com.esprit.cloudcraft.utils.QRCodeGenerator;
import com.google.zxing.WriterException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.security.Principal;
import java.time.LocalDate;
import java.util.List;
@Controller
@RestController
@CrossOrigin(origins = {"http://localhost:4200", "http://localhost:62699"})
@RequestMapping("/event")
public class EventController {
    private final IEventService eventService;
    @Autowired
    private FileStorageService fileStorageService;

    public EventController(IEventService eventService) {
        this.eventService = eventService;
    }
    @GetMapping("/getAll")
    public List<EventPayLoad> getAllEvents(){
        return EventMapper.geteventpayloadList( eventService.getallEvents());
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
        System.out.println(newEvent);
        System.out.println(picture);
        eventService.addEvent(newEvent, picture);
        return ResponseEntity.ok().build();
    }


    @PutMapping(value = "/update/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Object>  updateEvent(@PathVariable long id,@RequestParam String title, @RequestParam LocalDate dateBegin, @RequestParam LocalDate dateEnd, @RequestParam String location, @RequestParam String details, @RequestParam String description, @RequestParam MultipartFile picture) {
//        System.out.println(picture);

        Event newEvent = Event.builder()
                .title(title)
                .dateBegin(dateBegin)
                .dateEnd(dateEnd)
                .location(location)
                .details(details)
                .description(description)
                .build();

        eventService.updateEvent(id,newEvent,picture);
        return ResponseEntity.ok().build();
    }

//    @PutMapping("/update/{id}")
//    public void  updateEvent(@PathVariable long id  , @RequestBody Event e){
//        System.out.println(e);
//    }

    @PostMapping("/create")
    public ResponseEntity<Event>   createEvent(@RequestBody Event e){
        Event e1 =eventService.createEvent(e);
        return ResponseEntity.ok(e1);
    }



//@PutMapping("/update/{id}")
//    public Event updateEvent(@PathVariable long id  , @RequestBody Event e){
//System.out.println(e);
//        return eventService.updateEvent(id,e);
//}

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

    @GetMapping("/events/{id}")
    public Event findById(@PathVariable long id) {
        return eventService.findbyid(id);
    }
// /////////////////////////////// participation et annulation de participation //////////////////////////////////////////

    //// tell me if a user is already paticipation wala le
    @PostMapping("/{eventId}/issparticipate/{userId}")
    public ResponseEntity<String> isparticipateUserInEvent(@PathVariable Long eventId, Principal connectedUser) {
        try {
            User user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();
            eventService.IsparticipateUserInEvent(eventId, user.getId());
            return ResponseEntity.ok("User participated in the event successfully.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }


//    @PostMapping("/{eventId}/participate/{userId}")
//    public ResponseEntity<String> participateUserInEvent(@PathVariable Long eventId, @PathVariable Long userId) {
//        try {
//            eventService.participateUserInEvent(eventId, userId);
//            return ResponseEntity.ok("User participated in the event successfully.");
//        } catch (RuntimeException e) {
//            return ResponseEntity.status(400).body(e.getMessage());
//        }
//    }





    @PostMapping("/{eventId}/cancelParticipation/{userId}")
    public ResponseEntity<String> cancelUserParticipation(@PathVariable Long eventId, Principal connectedUser) {
        try {
            User user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();
            eventService.cancelUserParticipation(eventId, user.getId());
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














    @GetMapping("/{fileName}")
    public ResponseEntity<Resource> getImage(@PathVariable String fileName)    {
        // Construct the file path
        Path filePath =fileStorageService.getImagePath(fileName);
        Resource resource = null;
        try {
            resource = new UrlResource(filePath.toUri());
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
        // Check if the file exists
        if (!resource.exists()) {
            return ResponseEntity.notFound().build();
        }

        // Set content type as application/pdf
        HttpHeaders headers = new HttpHeaders();
        String contentType;
        if (fileName.toLowerCase().endsWith(".pdf")) {
            contentType = MediaType.APPLICATION_PDF_VALUE;
        } else if (fileName.toLowerCase().endsWith(".png") || fileName.toLowerCase().endsWith(".jpg") || fileName.toLowerCase().endsWith(".jpeg")) {
            contentType = MediaType.IMAGE_JPEG_VALUE; // You can use IMAGE_PNG_VALUE for PNG files
        } else {
            // Default to binary data if the file type is unknown
            contentType = MediaType.APPLICATION_OCTET_STREAM_VALUE;
        }
        headers.add(HttpHeaders.CONTENT_TYPE, contentType);

        // Return the file/image as ResponseEntity with appropriate headers
        return ResponseEntity.ok()
                .headers(headers)
                .body(resource);
    }


}

