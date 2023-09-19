package com.example.app.service.impl;

import com.example.app.dto.TaskDtoToCreate;
import com.example.app.dto.TaskDtoToUpdate;
import com.example.app.model.Task;
import com.example.app.repository.TaskRepository;
import com.example.app.service.FileService;
import com.example.app.service.GroupService;
import com.example.app.service.TaskService;
import com.example.app.service.TeacherService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;
    private final GroupService groupService;
    private final TeacherService teacherService;
    private final FileService fileService;
    @Override
    public Task findTaskByIdAndByStudentId(long studentId, long taskId) {
        return taskRepository.findTaskByIdAndByStudentId(studentId, taskId)
                .orElseThrow(() -> new RuntimeException("Task with id " + taskId + " is not available"));
    }

    @Override
    public List<Task> findAllTasksByStudentIdAndSubjectId(long studentId, long subjectId) {
        return taskRepository.findAllTasksByStudentIdAndSubjectId(studentId, subjectId);
    }

    @Override
    public Task findTaskByIdAndByTeacherId(long teacherId, long taskId) {
        return taskRepository.findTaskByIdAndByTeacherId(teacherId, taskId)
                .orElseThrow(() -> new RuntimeException("Task with id " + taskId + " is not available"));
    }

    @Override
    public Page<Task> findAllTasksByTeacherId(long teacherId, Pageable pageable) {
        return taskRepository.findAllTasksByTeacherId(teacherId, pageable);
    }

    @Override
    public Task create(TaskDtoToCreate taskDtoToCreate) {
        if (groupService.findGroupByIdAndByTeacherId(taskDtoToCreate.getTeacher().getId(),
                taskDtoToCreate.getGroup().getId()) == null) {
            throw new RuntimeException("It is not possible to create a task for this group, " +
                    "because it is assigned to another teacher");
        }
        Task task = new Task();
        task.setName(taskDtoToCreate.getName());
        task.setDescription(taskDtoToCreate.getDescription());
        task.setStartLine(taskDtoToCreate.getStartLine());
        task.setDeadLine(taskDtoToCreate.getDeadLine());
        task.setGroup(taskDtoToCreate.getGroup());
        task.setTeacher(taskDtoToCreate.getTeacher());
        return taskRepository.save(task);
    }

    @Override
    public void update(long id, TaskDtoToUpdate taskDtoToUpdate) {
        Task task = findTaskById(id);

        if (!task.getTeacher().equals(teacherService.findTeacherById(task.getTeacher().getId()))) {
            throw new RuntimeException("It is not possible to update this task, " +
                    "because it was created by another teacher");
        }

        task.setName(taskDtoToUpdate.getName());
        task.setDescription(taskDtoToUpdate.getDescription());
        task.setDeadLine(taskDtoToUpdate.getDeadLine());

        taskRepository.save(task);
    }

    @Override
    public void delete(long id) {
        if (taskRepository.existsById(id)) {
            taskRepository.deleteById(id);
        } else {
            throw new RuntimeException("Task with id " + id + " was not deleted");
        }
    }

    @Override
    public Task findTaskById(long id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task with id " + id + " was not found"));
    }

    @Override
    public void addFilesToTask(long taskId, MultipartFile[] files) {
        Task task = findTaskById(taskId);
        task.getFileUri().addAll(Arrays.stream(files)
                .map(fileService::uploadFile)
                .collect(Collectors.toSet()));
        taskRepository.save(task);
    }

    @Override
    public void deleteFileFromTask(long taskId, String fileUri) {
        Task task = findTaskById(taskId);
        task.getFileUri().remove(fileUri);
        taskRepository.save(task);
    }

    @Override
    public void save(Task task) {
        try {
            taskRepository.save(task);
        } catch (RuntimeException e) {
            log.error("Task " + task.getName() + " was not saved. Error: " + e.getLocalizedMessage());
        }
    }
}
