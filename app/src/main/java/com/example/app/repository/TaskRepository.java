package com.example.app.repository;

import com.example.app.model.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    @Transactional
    @Query(value = "SELECT t FROM Task t JOIN t.group.students s WHERE s.id = ?1 AND t.id = ?2")
    Optional<Task> findTaskByIdAndByStudentId(long studentId, long taskId);

    @Transactional
    @Query(value = "SELECT t FROM Task t JOIN t.teacher.subjects s JOIN t.group.students st " +
            "WHERE st.id = ?1 AND s.id = ?2")
    List<Task> findAllTasksByStudentIdAndSubjectId(long studentId, long subjectId);

    @Transactional
    @Query(value = "SELECT t FROM Task t WHERE t.teacher.id = ?1 AND t.id = ?2")
    Optional<Task> findTaskByIdAndByTeacherId(long teacherId, long taskId);

    Page<Task> findAllTasksByTeacherId(long teacherId, Pageable pageable);
}
