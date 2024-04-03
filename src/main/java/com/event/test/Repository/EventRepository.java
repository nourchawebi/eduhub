package com.event.test.Repository;

import com.event.test.Entity.Event;
import com.event.test.Enum.Name;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Long > {


   public Event findEventByCapacityAndIdEvent(long capacity, long idEvent);
   @Query("SELECT e FROM Event e JOIN e.club c WHERE c.name = :clubName AND e.title = :eventTitle")
   List<Event> findEventsByClubTitleAndEventName(@Param("clubName") Name clubName, @Param("eventTitle") String eventTitle);

   public List<Event> findAllByCapacityBetween(long startCapacity, long endCapacity);

   public List<Event> findAllByCapacity(long c);




}
