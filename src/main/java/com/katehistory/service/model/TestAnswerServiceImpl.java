package com.katehistory.service.model;

import com.katehistory.model.TestAnswer;
import com.katehistory.model.enums.AnswerType;
import com.katehistory.repository.TestAnswerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TestAnswerServiceImpl implements TestAnswerService {
    private final TestAnswerRepository testAnswerRepository;

    @Override
    public List<TestAnswer> getAllAnswers() {
        return testAnswerRepository.findAll();
    }

    @Override
    public List<TestAnswer> getAnswersByQuestionId(Long questionId) {
        return testAnswerRepository.findByQuestion_Id(questionId);
    }

    @Override
    public List<TestAnswer> getAnswersByType(AnswerType type) {
        return testAnswerRepository.findByAnswerType(type);
    }

    @Override
    public List<TestAnswer> getCorrectAnswers() {
        return testAnswerRepository.findByIsCorrectTrue();
    }

    @Override
    public TestAnswer getAnswerById(Long id) {
        return testAnswerRepository.findById(id).orElse(null); // можно заменить на исключение
    }

    @Override
    public TestAnswer createAnswer(TestAnswer answer) {
        return testAnswerRepository.save(answer);
    }

    @Override
    public TestAnswer updateAnswer(Long id, TestAnswer answerDetails) {
        Optional<TestAnswer> optionalAnswer = testAnswerRepository.findById(id);

        if (optionalAnswer.isPresent()) {
            TestAnswer answer = optionalAnswer.get();
            answer.setAnswerValue(answerDetails.getAnswerValue());
            answer.setExplanation(answerDetails.getExplanation());
            answer.setAnswerType(answerDetails.getAnswerType());
            answer.setIsCaseSensitive(answerDetails.getIsCaseSensitive());
            answer.setIsCorrect(answerDetails.getIsCorrect());

            return testAnswerRepository.save(answer);
        } else {
            return null; // или throw new AnswerNotFoundException()
        }
    }

    @Override
    public void deleteAnswer(Long id) {
        testAnswerRepository.deleteById(id);
    }
}
