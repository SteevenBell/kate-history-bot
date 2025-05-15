package com.katehistory.kate_history.service.model;

import com.katehistory.kate_history.model.Course;

import java.util.List;

public interface CourseService {
    List<Course> getAllCourses();

    List<Course> getPublishedCourses();

    List<Course> searchCourseByTitle(String title);

    Course getCourseById(Long id);

    Course createCourse(Course course);

    Course updateCourse(Long id, Course courseDetails);

    void deleteCourse(Long id);
}
