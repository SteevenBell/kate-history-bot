package com.katehistory.service.model;

import com.katehistory.model.Test;
import com.katehistory.model.enums.SubjectType;

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
