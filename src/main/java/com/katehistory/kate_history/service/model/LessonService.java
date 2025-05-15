package com.katehistory.kate_history.service.model;

import com.katehistory.kate_history.model.Lesson;
import com.katehistory.kate_history.model.enums.LessonType;

import java.time.LocalDateTime;
import java.util.List;

public interface LessonService {
    List<Lesson> getAllLessons();

    List<Lesson> getAvailableLessons();

    List<Lesson> getLessonsByType(LessonType type);

    List<Lesson> getLessonsInTimeRange(LocalDateTime from, LocalDateTime to);

    Lesson getLessonById(Long id);

    Lesson createLesson(Lesson lesson);

    Lesson updateLesson(Long id, Lesson lessonDetails);

    void deleteLesson(Long id);
}
