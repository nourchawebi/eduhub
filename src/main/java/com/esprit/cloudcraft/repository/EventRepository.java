package com.esprit.cloudcraft.repository;

import com.esprit.cloudcraft.entities.Event;
import com.esprit.cloudcraft.Enum.Name;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Long > {


   public Event findEventByCapacityAndIdEvent(long capacity, long idEvent);

   @Query("SELECT e.title, COUNT(eu) AS participation_count " +
           "FROM Event e " +
           "JOIN e.userSet eu " +
           "GROUP BY e.title " +
           "ORDER BY participation_count DESC")
   List<Object[]> findMostParticipatedEvent();


   @Query("SELECT MONTH(e.dateBegin) AS month, COUNT(*) AS eventCount " +
           "FROM Event e " +
           "GROUP BY MONTH(e.dateBegin) " +
           "ORDER BY eventCount DESC")
   List<Object[]> findMonthWithMostEvents();

   @Query("SELECT e FROM Event e JOIN e.club c WHERE c.name = :clubName AND e.title = :eventTitle")
   List<Event> findEventsByClubTitleAndEventName(@Param("clubName") Name clubName, @Param("eventTitle") String eventTitle);

   public List<Event> findAllByCapacityBetween(long startCapacity, long endCapacity);

   public List<Event> findAllByCapacity(long c);



}
