package com.example.app.service;

import com.example.app.dto.TaskDtoToCreate;
import com.example.app.dto.TaskDtoToUpdate;
import com.example.app.model.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

public interface TaskService {
    Task findTaskByIdAndByStudentId(long studentId, long taskId);
    List<Task> findAllTasksByStudentIdAndSubjectId(long studentId, long subjectId);
    Task findTaskByIdAndByTeacherId(long teacherId, long taskId);
    Page<Task> findAllTasksByTeacherId(long teacherId, Pageable pageable);
    Task create(TaskDtoToCreate taskDtoToCreate);
    void update(long id, TaskDtoToUpdate taskDtoToUpdate);
    void delete(long id);
    Task findTaskById(long id);
    void addFilesToTask(long taskId, MultipartFile[] files);
    void deleteFileFromTask(long taskId, String fileUri);
    void save(Task task);
}
