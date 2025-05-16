package com.katehistory.service.model;

import com.katehistory.model.TestAnswer;
import com.katehistory.model.enums.AnswerType;

import java.util.List;

public interface TestAnswerService {
    List<TestAnswer> getAllAnswers();

    List<TestAnswer> getAnswersByQuestionId(Long questionId);

    List<TestAnswer> getAnswersByType(AnswerType type);

    List<TestAnswer> getCorrectAnswers();

    TestAnswer getAnswerById(Long id);

    TestAnswer createAnswer(TestAnswer answer);

    TestAnswer updateAnswer(Long id, TestAnswer answerDetails);

    void deleteAnswer(Long id);
}
