package com.katehistory.kate_history.repository;

import com.katehistory.kate_history.model.UserAnswer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserAnswerRepository extends JpaRepository<UserAnswer, Long> {
    List<UserAnswer> findByUserId(Long userId);

    List<UserAnswer> findByTestId(Long testId);

    List<UserAnswer> findByQuestionId(Long questionId);

    List<UserAnswer> findByUserIdAndTestId(Long userId, Long testId);
}
