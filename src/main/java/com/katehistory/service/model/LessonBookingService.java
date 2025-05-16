package com.katehistory.service.model;

import com.katehistory.model.LessonBooking;
import com.katehistory.model.enums.BookingStatus;

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
