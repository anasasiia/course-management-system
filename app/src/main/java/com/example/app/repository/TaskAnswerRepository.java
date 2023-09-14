package com.example.app.repository;

import com.example.app.model.TaskAnswer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.List;

@Repository
public interface TaskAnswerRepository extends JpaRepository<TaskAnswer, Long> {
    @Transactional
    @Query(value = "SELECT a FROM Task_answers a WHERE a.student.id = ?1 AND a.task.id = ?2")
    Optional<TaskAnswer> findByStudentIdAndByTaskId(long studentId, long taskId);

    @Transactional
    @Query(value = "SELECT a FROM Task_answers a WHERE a.task.teacher.id = ?1 AND a.task.id = ?2")
    List<TaskAnswer> findByTeacherIdAndTaskId(long teacherId, long taskId);

}
