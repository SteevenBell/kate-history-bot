package com.katehistory.service.model;

import com.katehistory.model.TestQuestion;
import com.katehistory.model.enums.QuestionType;

import java.util.List;

public interface TestQuestionService {
    List<TestQuestion> getAllQuestions();

    List<TestQuestion> getQuestionsByTestId(Long testId);

    List<TestQuestion> getQuestionsByType(QuestionType type);

    TestQuestion getQuestionById(Long id);

    TestQuestion createQuestion(TestQuestion question);

    TestQuestion updateQuestion(Long id, TestQuestion questionDetails);

    void deleteQuestion(Long id);
}
