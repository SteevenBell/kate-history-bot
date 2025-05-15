package com.katehistory.kate_history.repository;

import com.katehistory.kate_history.model.Test;
import com.katehistory.kate_history.model.enums.SubjectType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TestRepository extends JpaRepository<Test, Long> {
    List<Test> findAllByIsPublishedTrue();

    List<Test> findBySubject(SubjectType subjectType);
}
