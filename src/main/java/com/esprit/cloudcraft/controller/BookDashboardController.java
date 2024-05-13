package com.esprit.cloudcraft.controller;
import com.esprit.cloudcraft.entities.AvailabilityType;
import com.esprit.cloudcraft.repository.BookDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Month;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@RestController
@RequestMapping("/bookstats")
@CrossOrigin(origins = "*")
public class BookDashboardController {
    @Autowired
    private BookDao bookDao ;


    @GetMapping("/totalBooks")
    public long countTotalBooks() {
        return bookDao.count();
    }

    @GetMapping("/availableBooks")
    public long countAvailableBooks() {
        return bookDao.countBooksByAvailability(AvailabilityType.AVAILABILE);
    }
    @GetMapping("/unavailableBooks")
    public long countUnavailableBooks() {
        return bookDao.countBooksByAvailability(AvailabilityType.NOT_AVAILABILE);
    }

    @GetMapping("/availableVsUnavailableBooks")
    public Map<String, Long> countAvailableVsUnavailableBooks() {
        Map<String, Long> result = new HashMap<>();
        result.put("Available", bookDao.countBooksByAvailability(AvailabilityType.AVAILABILE));
        result.put("Unavailable", bookDao.countBooksByAvailability(AvailabilityType.NOT_AVAILABILE));
        return result;
    }

    @GetMapping("/booksByCategory")
    public Map<String, Long> countBooksByCategory() {
        Map<String, Long> result = new HashMap<>();
        List<Object[]> counts = bookDao.countBooksByCategory();
        for (Object[] row : counts) {

            String category = (String) row[0];
            Long count = (Long) row[1];


            result.put(category, count);
        }
        return result;

    }

    @GetMapping("/booksCreationByMonth")
    public Map<String, Long> countBooksCreationByMonth() {
        Map<String, Long> result = new TreeMap<>();
        List<Object[]> counts = bookDao.countBooksByCreationMonthOfCurrentYear();
        for (Object[] row : counts) {
            Integer monthValue = (Integer) row[0];
            Month month = Month.of(monthValue);
            String monthName = month.toString();
            Long count = (Long) row[1];
            result.put(monthName, count);
        }
        return result;
    }


}
