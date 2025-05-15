package com.katehistory.kate_history.repository;

import com.katehistory.kate_history.model.TestQuestion;
import com.katehistory.kate_history.model.enums.QuestionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TestQuestionRepository extends JpaRepository<TestQuestion, Long> {
    List<TestQuestion> findByTest_Id(Long testId);

    List<TestQuestion> findByQuestionType(QuestionType type);
}
