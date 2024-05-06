package com.esprit.cloudcraft.services;


import com.esprit.cloudcraft.entities.Course;
import com.esprit.cloudcraft.entities.UneversityYear;
import com.esprit.cloudcraft.repository.ChapterRepo;
import com.esprit.cloudcraft.repository.CourseRepo;
import com.esprit.cloudcraft.repository.RatingRepo;
import com.esprit.cloudcraft.repository.SummaryRepo;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Month;
import java.util.*;

@Service
public class CourseDashboardService {
    @Resource
    private CourseRepo courseRepo;

    @Resource
    private SummaryRepo summaryRepo;

    @Resource
    private ChapterRepo chapterRepo;

    @Resource
    private RatingRepo ratingRepo;


    public Map<String,Long>  countResourseByType(){
        Map<String,Long> resoursesCountByType=new HashMap<>();
        long coursesCount =courseRepo.countBy();
        long chapterCount =chapterRepo.countBy();
        long summaryCount =summaryRepo.countBy();
        resoursesCountByType.put("courses",coursesCount);
        resoursesCountByType.put("chapters",chapterCount);
        resoursesCountByType.put("summaries",summaryCount);
            return resoursesCountByType;


    }

    public Map<UneversityYear,Long>  getCoursesbyYear(){
        Map<UneversityYear,Long> coursesByYear=new HashMap<>();
        List<Course> courses  =courseRepo.findAll();
        UneversityYear[] values = UneversityYear.values();
        for (UneversityYear value : values) {
        coursesByYear.put(value,0L);
        }

        System.out.println(coursesByYear);
        System.out.println(courses);

        courses.stream().forEach(course -> {
            UneversityYear key=course.getUneversityYear();
            if(key==null) key=values[0];
            long previousValue=coursesByYear.get(key);
            coursesByYear.put(key,previousValue+1 );

        });

        return coursesByYear;


    }
    public Map<String,Long>  getRatingsbyValue(){
        List<Object[]> artingsbyValue=ratingRepo.countRatingsByValue();
        Map<String, Long> result = new TreeMap<>();

        for (Object[] row : artingsbyValue) {
            String  countString= (int)row[0] + " Stars";
            result.put(countString, (Long)row[1]);
        }
        return result;
    }

    public Map<String,Long> getCoursesByRatings(){
        List<Map<String ,Object>> counts = courseRepo.countCoursesByRatingValue();
        Map<String,Long> result=new TreeMap<>();
        for (Map<String, Object> item : counts) {
            int rating = (int) item.get("rating");
            long courseCount = (long) item.get("courseCount");

            String ratingCategory;
            switch (rating) {
                case 5:
                    ratingCategory = "Highly rated";
                    break;
                case 4:
                    ratingCategory = "Moderately rated";
                    break;
                case 3:
                    ratingCategory = "Neutral rated";
                    break;
                case 2:
                    ratingCategory = "Low rated";
                    break;
                case 1:
                    ratingCategory = "Poorly rated";
                    break;
                default:
                    ratingCategory = "Unknown rating";
                    break;
            }
            result.put(ratingCategory, courseCount);
        }
        return result;
    }


    public long getCoursesNumber(){
        return summaryRepo.countBy();
    }
    public long getChaptersNumber(){
        return chapterRepo.countBy();
    }
    public long getSummariesNumber(){
        return summaryRepo.countBy();
    }




}
