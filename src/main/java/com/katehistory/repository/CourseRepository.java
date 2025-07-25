package com.katehistory.repository;

import com.katehistory.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CourseRepository extends JpaRepository<Course, Long> {
    List<Course> findAllByIsPublishedTrue();

    List<Course> findByTitleContainingIgnoreCase(String title);
}
