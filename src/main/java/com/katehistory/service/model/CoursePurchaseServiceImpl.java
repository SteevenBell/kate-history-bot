package com.katehistory.service.model;

import com.katehistory.model.CoursePurchase;
import com.katehistory.model.enums.PaymentStatus;
import com.katehistory.repository.CoursePurchaseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CoursePurchaseServiceImpl implements CoursePurchaseService {
    private final CoursePurchaseRepository coursePurchaseRepository;

    @Override
    public List<CoursePurchase> getAllPurchases() {
        return coursePurchaseRepository.findAll();
    }

    @Override
    public List<CoursePurchase> getPurchasesByUser(Long userId) {
        return coursePurchaseRepository.findByUserId(userId);
    }

    @Override
    public List<CoursePurchase> getPurchasesByCourse(Long courseId) {
        return coursePurchaseRepository.findByCourseId(courseId);
    }

    @Override
    public List<CoursePurchase> getPurchasesByStatus(PaymentStatus status) {
        return coursePurchaseRepository.findByPaymentStatus(status);
    }

    @Override
    public CoursePurchase getPurchaseById(Long id) {
        return coursePurchaseRepository.findById(id).orElse(null); // или бросай исключение
    }

    @Override
    public CoursePurchase createPurchase(CoursePurchase purchase) {
        return coursePurchaseRepository.save(purchase);
    }

    @Override
    public CoursePurchase updatePurchase(Long id, CoursePurchase purchaseDetails) {
        Optional<CoursePurchase> optionalPurchase = coursePurchaseRepository.findById(id);

        if (optionalPurchase.isPresent()) {
            CoursePurchase purchase = optionalPurchase.get();
            purchase.setPaymentStatus(purchaseDetails.getPaymentStatus());
            purchase.setAmountPaid(purchaseDetails.getAmountPaid());
            purchase.setAccessExpiresAt(purchaseDetails.getAccessExpiresAt());

            return coursePurchaseRepository.save(purchase);
        } else {
            return null;
        }
    }

    @Override
    public void deletePurchase(Long id) {
        coursePurchaseRepository.deleteById(id);
    }
}
