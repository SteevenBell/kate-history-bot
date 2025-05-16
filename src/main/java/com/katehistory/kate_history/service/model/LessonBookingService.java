package com.katehistory.kate_history.service.model;

import com.katehistory.kate_history.model.LessonBooking;
import com.katehistory.kate_history.model.enums.BookingStatus;

import java.util.List;

public interface LessonBookingService {
    List<LessonBooking> getAllBookings();

    List<LessonBooking> getBookingsByUser(Long userId);

    List<LessonBooking> getBookingsByLesson(Long lessonId);

    List<LessonBooking> getBookingsByStatus(BookingStatus status);

    LessonBooking getBookingById(Long id);

    LessonBooking createBooking(LessonBooking booking);

    LessonBooking updateBooking(Long id, LessonBooking bookingDetails);

    void deleteBooking(Long id);
}
