package com.katehistory.service.model;

import com.katehistory.model.UserAnswer;

import java.util.List;

public interface UserAnswerService {
    List<UserAnswer> getAllAnswers();

    List<UserAnswer> getAnswersByUser(Long userId);

    List<UserAnswer> getAnswersByTest(Long testId);

    List<UserAnswer> getAnswersByUserAndTest(Long userId, Long testId);

    UserAnswer saveAnswer(UserAnswer answer);

    void deleteAnswer(Long id);
}
