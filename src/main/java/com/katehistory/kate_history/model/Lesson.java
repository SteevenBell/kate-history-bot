package com.katehistory.kate_history.model;

import com.katehistory.kate_history.model.enums.LessonType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "lessons")
public class Lesson extends BaseEntity {
    @Column(name = "title")
    private String title;

    @Enumerated(EnumType.STRING)
    @Column(name = "lesson_type")
    private LessonType lessonType;

    @Column(name = "max_participants")
    private Integer maxParticipants = 1; // по умолчанию — индивидуальное занятие

    @Column(name = "start_time")
    private LocalDateTime startTime;

    @Column(name = "end_time")
    private LocalDateTime endTime;

    @Column(name = "is_available")
    private Boolean isAvailable = true;
}
