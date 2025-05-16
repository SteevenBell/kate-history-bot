package com.katehistory.service.model;

import com.katehistory.model.CoursePurchase;
import com.katehistory.model.enums.PaymentStatus;

import java.util.List;

public interface CoursePurchaseService {
    List<CoursePurchase> getAllPurchases();

    List<CoursePurchase> getPurchasesByUser(Long userId);

    List<CoursePurchase> getPurchasesByCourse(Long courseId);

    List<CoursePurchase> getPurchasesByStatus(PaymentStatus status);

    CoursePurchase getPurchaseById(Long id);

    CoursePurchase createPurchase(CoursePurchase purchase);

    CoursePurchase updatePurchase(Long id, CoursePurchase purchaseDetails);

    void deletePurchase(Long id);
}
