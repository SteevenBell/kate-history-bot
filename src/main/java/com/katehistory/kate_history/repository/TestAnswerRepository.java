package com.katehistory.kate_history.repository;

import com.katehistory.kate_history.model.TestAnswer;
import com.katehistory.kate_history.model.enums.AnswerType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TestAnswerRepository extends JpaRepository<TestAnswer, Long> {
    List<TestAnswer> findByQuestion_Id(Long questionId);

    List<TestAnswer> findByAnswerType(AnswerType type);

    List<TestAnswer> findByIsCorrectTrue();

    List<TestAnswer> findByIsCorrectFalse();
}
