package com.esprit.cloudcraft.controller;

import com.esprit.cloudcraft.entities.AvailabilityType;
import com.esprit.cloudcraft.entities.UneversityYear;
import com.esprit.cloudcraft.repository.BookDao;
import com.esprit.cloudcraft.services.CourseDashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Month;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;


@RestController
@RequestMapping("course-dashboard")
public class CoursesDashboardController {
    @Autowired
    private BookDao bookDao ;

    @Autowired
    private CourseDashboardService courseDashboardService;



    //@GetMapping("/totalBooks")
    public long countTotalBooks() {
        return bookDao.count();
    }

    //@GetMapping("/availableBooks")
    public long countAvailableBooks() {
        return bookDao.countBooksByAvailability(AvailabilityType.AVAILABILE);
    }
    //@GetMapping("/unavailableBooks")
    public long countUnavailableBooks() {
        return bookDao.countBooksByAvailability(AvailabilityType.NOT_AVAILABILE);
    }

    //@GetMapping("/availableVsUnavailableBooks")
    public Map<String, Long> countAvailableVsUnavailableBooks() {
        Map<String, Long> result = new HashMap<>();
        result.put("Available", bookDao.countBooksByAvailability(AvailabilityType.AVAILABILE));
        result.put("Unavailable", bookDao.countBooksByAvailability(AvailabilityType.NOT_AVAILABILE));
        return result;
    }

    //@GetMapping("/booksByCategory")
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


    @GetMapping("/coursesByYear")
    public Map<UneversityYear, Long> countCoursesByYear() {
        System.out.println("REACHIN HERE :+)");
        return courseDashboardService.getCoursesbyYear();

    }

    @GetMapping("/ratingsByValue")
    public Map<String, Long> contRatingsByValue() {
        System.out.println("REACHIN HERE :+)");
        return courseDashboardService.getRatingsbyValue();
    }
    @GetMapping("/coursesByRatings")
    public Map<String, Long> getCoursesByRating() {
        Map<String, Long> result = new TreeMap<>();

       return courseDashboardService.getCoursesByRatings();

    }

    @GetMapping("/coursesCount")
    public Long getCoursesNumber() {

        return courseDashboardService.getCoursesNumber();

    }
    @GetMapping("/chaptresCount")
    public Long getChaptersNumber() {


        return courseDashboardService.getChaptersNumber();

    }
    @GetMapping("/summariesCount")
    public Long getSummariesNumber() {


        return courseDashboardService.getSummariesNumber();

    }
}
