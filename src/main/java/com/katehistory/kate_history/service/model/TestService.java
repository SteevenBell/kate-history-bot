package com.katehistory.kate_history.service.model;

import com.katehistory.kate_history.model.Test;
import com.katehistory.kate_history.model.enums.SubjectType;

import java.util.List;

public interface TestService {
    List<Test> getAllTests();

    List<Test> getPublishedTests();

    List<Test> getTestsBySubject(SubjectType subject);

    Test getTestById(Long id);

    Test createTest(Test test);

    Test updateTest(Long id, Test testDetails);

    void deleteTest(Long id);
}
