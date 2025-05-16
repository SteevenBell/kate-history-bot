package com.katehistory.service.model;

import com.katehistory.model.Lesson;
import com.katehistory.model.enums.LessonType;
import com.katehistory.repository.LessonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LessonServiceImpl implements LessonService {
    private final LessonRepository lessonRepository;

    @Override
    public List<Lesson> getAllLessons() {
        return lessonRepository.findAll();
    }

    @Override
    public List<Lesson> getAvailableLessons() {
        return lessonRepository.findAllByIsAvailableTrue();
    }

    @Override
    public List<Lesson> getLessonsByType(LessonType type) {
        return lessonRepository.findByLessonType(type);
    }

    @Override
    public List<Lesson> getLessonsInTimeRange(LocalDateTime from, LocalDateTime to) {
        return lessonRepository.findByStartTimeBetween(from, to);
    }

    @Override
    public Lesson getLessonById(Long id) {
        return lessonRepository.findById(id).orElse(null); // или бросай исключение
    }

    @Override
    public Lesson createLesson(Lesson lesson) {
        return lessonRepository.save(lesson);
    }

    @Override
    public Lesson updateLesson(Long id, Lesson lessonDetails) {
        Optional<Lesson> optionalLesson = lessonRepository.findById(id);
        if (optionalLesson.isPresent()) {
            Lesson lesson = optionalLesson.get();
            lesson.setTitle(lessonDetails.getTitle());
            lesson.setLessonType(lessonDetails.getLessonType());
            lesson.setMaxParticipants(lessonDetails.getMaxParticipants());
            lesson.setStartTime(lessonDetails.getStartTime());
            lesson.setEndTime(lessonDetails.getEndTime());
            lesson.setIsAvailable(lessonDetails.getIsAvailable());

            return lessonRepository.save(lesson);
        } else {
            return null;
        }
    }

    @Override
    public void deleteLesson(Long id) {
        lessonRepository.deleteById(id);
    }
}
