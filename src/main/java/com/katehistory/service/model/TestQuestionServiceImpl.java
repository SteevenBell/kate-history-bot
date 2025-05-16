package com.katehistory.service.model;

import com.katehistory.model.TestQuestion;
import com.katehistory.model.enums.QuestionType;
import com.katehistory.repository.TestQuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TestQuestionServiceImpl implements TestQuestionService{
    private final TestQuestionRepository questionRepository;

    @Override
    public List<TestQuestion> getAllQuestions() {
        return questionRepository.findAll();
    }

    @Override
    public List<TestQuestion> getQuestionsByTestId(Long testId) {
        return questionRepository.findByTest_Id(testId);
    }

    @Override
    public List<TestQuestion> getQuestionsByType(QuestionType type) {
        return questionRepository.findByQuestionType(type);
    }

    @Override
    public TestQuestion getQuestionById(Long id) {
        return questionRepository.findById(id).orElse(null); // или бросать исключение
    }

    @Override
    public TestQuestion createQuestion(TestQuestion question) {
        return questionRepository.save(question);
    }

    @Override
    public TestQuestion updateQuestion(Long id, TestQuestion questionDetails) {
        Optional<TestQuestion> optionalQuestion = questionRepository.findById(id);

        if (optionalQuestion.isPresent()) {
            TestQuestion question = optionalQuestion.get();
            question.setQuestionText(questionDetails.getQuestionText());
            question.setQuestionType(questionDetails.getQuestionType());
            question.setImageUrl(questionDetails.getImageUrl());
            question.setWeight(questionDetails.getWeight());
            question.setOrderNum(questionDetails.getOrderNum());

            return questionRepository.save(question);
        } else {
            return null;
        }
    }

    @Override
    public void deleteQuestion(Long id) {
        questionRepository.deleteById(id);
    }
}
