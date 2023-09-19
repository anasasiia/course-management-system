package com.example.app.repository;

import com.example.app.model.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface RatingRepository extends JpaRepository<Rating, Long> {
    Optional<Rating> findByTaskIdAndStudentId(long taskId, long studentId);

    @Transactional
    @Query(value = "SELECT r FROM Rating r JOIN r.subject.teachers t WHERE r.task.id = ?1 AND t.id = ?2")
    List<Rating> findByTaskIdAndTeacherId(long taskId, long teacherId);

    @Transactional
    @Query(value = "SELECT r FROM Rating r JOIN r.subject.teachers t WHERE r.id = ?1 AND t.id = ?2")
    Optional<Rating> findByIdAndTeacherId(long id, long teacherId);

    List<Rating> findBySubjectIdAndStudentId(long subjectId, long studentId);
}
