package org.cloudcraft.coursemanagementservice.dto;

import org.cloudcraft.coursemanagementservice.module.*;

import java.util.ArrayList;
import java.util.List;

public class PayloadSerialization {
    //Rating
        //RatingPayload->Rating
    public static Rating getRatingFromRatingPayload(RatingPayload ratingPayload){
        return Rating.builder()
                .ratingId(ratingPayload.getId())
                .title(ratingPayload.getTitle())
                .value(ratingPayload.getValue())
                .createdAt(ratingPayload.getCreatedAt())
                .content(ratingPayload.getContent())
                .modifiedAt(ratingPayload.getCreatedAt())
                .build();
    }

    public static RatingPayload prepareRatingResponse(Rating rating){
        return RatingPayload.builder()
                .id(rating.getRatingId())
                .createdAt(rating.getCreatedAt())
                .value(rating.getValue())
                .title(rating.getTitle())
                .content(rating.getContent())
                .build();
    }
    public static List<RatingPayload> prepareRatingResponselist(List<Rating> ratings){
        List<RatingPayload> ratingResponseList=new ArrayList<>();
        if(ratings==null) return ratingResponseList;
        for(Rating rating:ratings){
            ratingResponseList.add(prepareRatingResponse(rating));
        }
        return ratingResponseList;
    }
    // Courses
        //CoursePayload -> Course
    public static Course getCourseFromCourseRequest(CourseRequest courseRequest){
        return Course.builder()
                .name(courseRequest.getName())
                .description(courseRequest.getDescription())
                .uneversityYear(courseRequest.getUneversityYear())
                .courseCategory(courseRequest.getCourseCategory())
                .build();
    }
        //List<CoursePayload> -> List<Course>
    public static List<Course> getCourseListFromCourseRequestList(List<CourseRequest> CourseRequestList){
        List<Course> courses=new ArrayList<>();
        for(CourseRequest courseRequest:CourseRequestList){
            courses.add(getCourseFromCourseRequest(courseRequest));
        }
        return courses;
    }

    //Course -> CoursePayload
    public static CourseResponse prepeareCourseResponse(Course course){
        return CourseResponse.builder()
                .name(course.getName())
                .id(course.getCourseId())
                .image(prepareFileEntityResponse(course.getImage()))
                .build();
    }
    //List<Course> -> List<CoursePayload>
    public static List<CourseResponse> prepeareListCoursePayload(List<Course> courses){
        List<CourseResponse> courseResponseList =new ArrayList<>();
        for(Course course:courses){
            courseResponseList.add (
                    CourseResponse.builder()
                    .name(course.getName())
                            .id(course.getCourseId())
                            .image(prepareFileEntityResponse(course.getImage()))
                            .description(course.getDescription())
                            .courseCategory(course.getCourseCategory())
                    .id(course.getCourseId())
                    .build());
        }
        return   courseResponseList;
    }
    public static CourseDetailedResponse prepeareCourseDetailedResponse(Course course){
        return CourseDetailedResponse.builder()
                .name(course.getName())
                .id(course.getCourseId())
                .description(course.getDescription())
                .courseCategory(course.getCourseCategory())
                .uneversityYear(course.getUneversityYear())
                .image(prepareFileEntityResponse(course.getImage()))
                .chapters(prepareChapterResponseList(course.getChapters()))
                .summaries(prepareSummaryResponseList(course.getSummaries()))
                .ratings(prepareRatingResponselist(course.getRating()))
                .build();
    }



    //Chapters
    public static ChapterResponse prepareChapterResponse(Chapter chapter){
        return ChapterResponse.builder()
                .id(chapter.getChapterId())
                .content(prepareContentResponseList(chapter.getChapterContent()))
                .title(chapter.getTitle())
                .description(chapter.getDescription())
                .build();
    }
    public static List<ChapterResponse> prepareChapterResponseList(List<Chapter> chapters ){
        List<ChapterResponse> chapterResponseList =new ArrayList<>();
        for(Chapter chapter:chapters){
            chapterResponseList.add(prepareChapterResponse(chapter));
        }
        return chapterResponseList;
    }

    //Content
    public static ContentResponse prepareContentResponse(Content content){
        return ContentResponse.builder()
                .contentId(content.getContentId())
                .contentDescription(content.getContentDescription())
                .contentCategory(content.getContentCategory())
                .contentTitle(content.getContentTitle())
                .files(prepareFileEntityResponseList(content.getFiles()))
                .build();
    }

    public static List<ContentResponse> prepareContentResponseList(List<Content> contents){
        List<ContentResponse> contentResponseList =new ArrayList<>();
        if(contents==null) return contentResponseList;
        for(Content content:contents){
            contentResponseList.add(
                    prepareContentResponse(content)
            );
        }
        return contentResponseList;
    }
//    public static Content getContentFromContentRequest(ContentRequest contentRequest){
//        return Content.builder()
//                .fileLocation(contentPayload.getFileLocation())
//                .contentId(contentPayload.getContentId())
//                .fileName(contentPayload.getFileName())
//                .contentType(contentPayload.getContentType())
//                .contentCategory(contentPayload.getContentCategory())
//                .build();
//    }


    public static List<FileEntityResponse> prepareFileEntityResponseList(List<FileEntity> files){
        List<FileEntityResponse> responseFiles=new ArrayList<>();
        if(files==null) return  responseFiles;
        for(FileEntity file:files){
            responseFiles.add(
                    prepareFileEntityResponse(file)
            );
        }
        return responseFiles;

    }
    public static FileEntityResponse prepareFileEntityResponse(FileEntity file){
        return FileEntityResponse
                .builder()
                .url(file.getUrl())
                .fileId(file.getFileId())
                .fileLocation(file.getFileLocation())
                .fileName(file.getFileName())
                .build();

}


public static SummaryResponse prepareSummaryResponse(Summary summary){
        System.out.println(summary);
return SummaryResponse
        .builder()
        .id(summary.getSummaryId())
        .files(prepareFileEntityResponseList(summary.getFiles()))
        .description(summary.getDescription())
        .title(summary.getTitle())
        .build();
}
    public static SummaryResponse prepareSummaryDetailedResponse(Summary summary){
        System.out.println(summary);
        return SummaryResponse
                .builder()
                .id(summary.getSummaryId())
                .ratings(summary.getRatings())
                .files(prepareFileEntityResponseList(summary.getFiles()))
                .description(summary.getDescription())
                .title(summary.getTitle())
                .build();
    }


    public static Summary getSummaryFromSummaryRequest(SummaryRequest summaryRequest){
        return Summary
                .builder()
                .description(summaryRequest.getDescription())
                .title(summaryRequest.getTitle())
                .build();
    }
    public static List<SummaryResponse> prepareSummaryResponseList(List<Summary> summaries){
        List<SummaryResponse> summaryResponseList=new ArrayList<>();
        if(summaries==null) return summaryResponseList;
        for(Summary summary:summaries){
            summaryResponseList.add(prepareSummaryResponse(summary));
        }
        return summaryResponseList;
    }


    public static ChapterDetailedResponse prepareChapterDetailedResponse(Chapter chapter) {
        return ChapterDetailedResponse.builder()
                .id(chapter.getChapterId())
                .content(prepareContentResponseList(chapter.getChapterContent()))
                .summaries(prepareSummaryResponseList(chapter.getSummaries()))
                .ratings(prepareRatingResponselist(chapter.getRatings()))
                .title(chapter.getTitle())
                .description(chapter.getDescription())
                .build();
    }
}
