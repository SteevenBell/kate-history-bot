package com.katehistory.repository;

import com.katehistory.model.Test;
import com.katehistory.model.enums.SubjectType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TestRepository extends JpaRepository<Test, Long> {
    List<Test> findAllByIsPublishedTrue();

    List<Test> findBySubject(SubjectType subjectType);
}
