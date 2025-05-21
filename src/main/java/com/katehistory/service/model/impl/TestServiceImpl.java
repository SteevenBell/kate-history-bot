package com.katehistory.service.model.impl;

import com.katehistory.model.Test;
import com.katehistory.model.enums.SubjectType;
import com.katehistory.repository.TestRepository;
import com.katehistory.service.model.TestService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TestServiceImpl implements TestService {

    private final TestRepository testRepository;

    @Override
    public List<Test> getAllTests() {
        return testRepository.findAll();
    }

    @Override
    public List<Test> getPublishedTests() {
        return testRepository.findAllByIsPublishedTrue();
    }

    @Override
    public List<Test> getTestsBySubject(SubjectType subject) {
        return testRepository.findBySubject(subject);
    }

    @Override
    public Test getTestById(Long id) {
        return testRepository.findById(id).orElse(null);
    }

    @Override
    public Test createTest(Test test) {
        return testRepository.save(test);
    }

    @Override
    public Test updateTest(Long id, Test testDetails) {
        Optional<Test> testOptional = testRepository.findById(id);
        if (testOptional.isPresent()) {
            Test test = testOptional.get();
            test.setTitle(testDetails.getTitle());
            test.setSubject(testDetails.getSubject());
            test.setDescription(testDetails.getDescription());
            test.setIsPublished(testDetails.getIsPublished());

            return testRepository.save(test);
        } else {
            return null;
        }
    }

    @Override
    public void deleteTest(Long id) {
        testRepository.deleteById(id);
    }
}
