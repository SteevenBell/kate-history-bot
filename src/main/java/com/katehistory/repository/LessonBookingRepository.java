package com.katehistory.repository;

import com.katehistory.model.LessonBooking;
import com.katehistory.model.enums.BookingStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LessonBookingRepository extends JpaRepository<LessonBooking, Long> {
    List<LessonBooking> findByUserId(Long userId);

    List<LessonBooking> findByLessonId(Long lessonId);

    List<LessonBooking> findByBookingStatus(BookingStatus status);

    List<LessonBooking> findByUserIdAndBookingStatus(Long userId, BookingStatus status);
}
