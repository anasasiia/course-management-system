package com.example.app.repository;

import com.example.app.model.Subject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface SubjectRepository extends JpaRepository<Subject, Long> {
    @Transactional
    @Query(value = "SELECT s " +
            "FROM Subjects s " +
            "JOIN s.teachers t " +
            "WHERE t.id = ?1 AND s.id = subjectName")
    Page<Subject> findSubjectByNameAndByTeacherId(long teacherId, String subjectName, Pageable pageable);

    @Transactional
    @Query(value = "SELECT s " +
            "FROM Subjects s " +
            "JOIN s.teachers t " +
            "WHERE t.id = ?1")
    Page<Subject> findAllSubjectsByTeacherId(long teacherId, Pageable pageable);

    @Transactional
    @Query(value = "SELECT s " +
            "FROM Subjects s " +
            "JOIN s.groups g " +
            "WHERE g.id = ?1")
    Page<Subject> findAllSubjectsByGroupId(long groupId, Pageable pageable);

    Page<Subject> findAll(Pageable pageable);
}
