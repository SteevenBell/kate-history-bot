package com.katehistory.kate_history.service.model;

import com.katehistory.kate_history.model.LessonBooking;
import com.katehistory.kate_history.model.enums.BookingStatus;
import com.katehistory.kate_history.repository.LessonBookingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LessonBookingServiceImpl implements LessonBookingService {
    private final LessonBookingRepository lessonBookingRepository;

    @Override
    public List<LessonBooking> getAllBookings() {
        return lessonBookingRepository.findAll();
    }

    @Override
    public List<LessonBooking> getBookingsByUser(Long userId) {
        return lessonBookingRepository.findByUserId(userId);
    }

    @Override
    public List<LessonBooking> getBookingsByLesson(Long lessonId) {
        return lessonBookingRepository.findByLessonId(lessonId);
    }

    @Override
    public List<LessonBooking> getBookingsByStatus(BookingStatus status) {
        return lessonBookingRepository.findByBookingStatus(status);
    }

    @Override
    public LessonBooking getBookingById(Long id) {
        return lessonBookingRepository.findById(id).orElse(null); // или throw new RuntimeException()
    }

    @Override
    public LessonBooking createBooking(LessonBooking booking) {
        return lessonBookingRepository.save(booking);
    }

    @Override
    public LessonBooking updateBooking(Long id, LessonBooking bookingDetails) {
        Optional<LessonBooking> optionalBooking = lessonBookingRepository.findById(id);

        if (optionalBooking.isPresent()) {
            LessonBooking booking = optionalBooking.get();
            booking.setBookingStatus(bookingDetails.getBookingStatus());

            return lessonBookingRepository.save(booking);
        } else {
            return null;
        }
    }

    @Override
    public void deleteBooking(Long id) {
        lessonBookingRepository.deleteById(id);
    }
}
