package com.katehistory.service.model;

import com.katehistory.model.Course;

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
