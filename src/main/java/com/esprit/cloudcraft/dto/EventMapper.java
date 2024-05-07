package com.esprit.cloudcraft.dto;

import com.esprit.cloudcraft.entities.Event;

import java.util.List;

public class EventMapper {
    static public EventPayLoad geteventpayload(Event ev){
        return EventPayLoad.builder().idEvent(ev.getIdEvent()).title(ev.getTitle()).dateBegin(ev.getDateBegin()).dateEnd(ev.getDateEnd()).capacity(ev.getCapacity()).location(ev.getLocation()).details(ev.getDetails()).description(ev.getDescription()).imageURL("http://localhost:8081/event/"+ev.getPicture()).build();
    }

    static public List<EventPayLoad> geteventpayloadList(List<Event> evn){
        return evn.stream().map(event -> geteventpayload(event)).toList();
    }

}
