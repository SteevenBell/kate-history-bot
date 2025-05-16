package com.katehistory.kate_history.repository;

import com.katehistory.kate_history.model.CoursePurchase;
import com.katehistory.kate_history.model.enums.PaymentStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CoursePurchaseRepository extends JpaRepository<CoursePurchase, Long> {
    List<CoursePurchase> findByUserId(Long userId);

    List<CoursePurchase> findByCourseId(Long courseId);

    List<CoursePurchase> findByPaymentStatus(PaymentStatus status);

    List<CoursePurchase> findByUserIdAndPaymentStatus(Long userId, PaymentStatus status);
}
