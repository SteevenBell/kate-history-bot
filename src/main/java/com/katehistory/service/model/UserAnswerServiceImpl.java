package com.katehistory.service.model;

import com.katehistory.model.UserAnswer;
import com.katehistory.repository.UserAnswerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserAnswerServiceImpl implements UserAnswerService {
    private final UserAnswerRepository userAnswerRepository;

    @Override
    public List<UserAnswer> getAllAnswers() {
        return userAnswerRepository.findAll();
    }

    @Override
    public List<UserAnswer> getAnswersByUser(Long userId) {
        return userAnswerRepository.findByUserId(userId);
    }

    @Override
    public List<UserAnswer> getAnswersByTest(Long testId) {
        return userAnswerRepository.findByTestId(testId);
    }

    @Override
    public List<UserAnswer> getAnswersByUserAndTest(Long userId, Long testId) {
        return userAnswerRepository.findByUserIdAndTestId(userId, testId);
    }

    @Override
    public UserAnswer saveAnswer(UserAnswer answer) {
        return userAnswerRepository.save(answer);
    }

    @Override
    public void deleteAnswer(Long id) {
        userAnswerRepository.deleteById(id);
    }
}
