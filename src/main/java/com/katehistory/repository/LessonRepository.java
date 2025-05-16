package com.katehistory.repository;

import com.katehistory.model.Lesson;
import com.katehistory.model.enums.LessonType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface LessonRepository extends JpaRepository<Lesson, Long> {
    List<Lesson> findAllByIsAvailableTrue();

    List<Lesson> findByLessonType(LessonType type);

    List<Lesson> findByStartTimeBetween(LocalDateTime start, LocalDateTime end);
}
