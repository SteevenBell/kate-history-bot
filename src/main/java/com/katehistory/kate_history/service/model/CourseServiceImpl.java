package com.katehistory.kate_history.service.model;

import com.katehistory.kate_history.model.Course;
import com.katehistory.kate_history.repository.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {
    private final CourseRepository courseRepository;

    @Override
    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    @Override
    public List<Course> getPublishedCourses() {
        return courseRepository.findAll();
    }

    @Override
    public List<Course> searchCourseByTitle(String title) {
        return courseRepository.findByTitleContainingIgnoreCase(title);
    }

    @Override
    public Course getCourseById(Long id) {
        return courseRepository.findById(id).orElse(null);
    }

    @Override
    public Course createCourse(Course course) {
        return courseRepository.save(course);
    }

    @Override
    public Course updateCourse(Long id, Course courseDetails) {
        Optional<Course> courseOptional = courseRepository.findById(id);
        if (courseOptional.isPresent()) {
            Course course = courseOptional.get();
            course.setTitle(courseDetails.getTitle());
            course.setDescription(courseDetails.getDescription());
            course.setPrice(courseDetails.getPrice());
            course.setDurationDays(courseDetails.getDurationDays());
            course.setIsPublished(courseDetails.getIsPublished());

            return courseRepository.save(course);
        } else {
            return null;
        }
    }

    @Override
    public void deleteCourse(Long id) {

    }
}
