package com.example.app.service.impl;

import com.example.app.dto.TaskAnswerDto;
import com.example.app.model.TaskAnswer;
import com.example.app.repository.TaskAnswerRepository;
import com.example.app.service.StudentService;
import com.example.app.service.TaskAnswerService;
import com.example.app.service.TaskService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class TaskAnswerServiceImpl implements TaskAnswerService {

    private final TaskAnswerRepository taskAnswerRepository;
    private final StudentService studentService;
    private final TaskService taskService;
    @Override
    public TaskAnswer findByStudentIdAndByTaskId(long studentId, long taskId) {
        return taskAnswerRepository.findByStudentIdAndByTaskId(studentId, taskId)
                .orElseThrow(() -> new RuntimeException("Answer for the task with id " + taskId + " is not found"));
    }

    @Override
    public List<TaskAnswer> findByTeacherIdAndTaskId(long teacherId, long taskId) {
        return taskAnswerRepository.findByTeacherIdAndTaskId(teacherId, taskId);
    }

    @Override
    public void addFileToAnswer(long answerId, MultipartFile[] files) {
        TaskAnswer taskAnswer = findById(answerId);
        taskAnswer.getAnswersUri().addAll(Arrays.stream(files)
                .map(FileService::uploadFiles)
                .collect(Collectors.toSet()));
        taskAnswerRepository.save(taskAnswer);
    }

    @Override
    public void deleteFileFromAnswer(long answerId, String fileUri) {
        TaskAnswer taskAnswer = findById(answerId);
        taskAnswer.getAnswersUri().remove(fileUri);
        taskAnswerRepository.save(taskAnswer);
    }

    @Override
    public TaskAnswer create(TaskAnswerDto taskAnswerDto) {
        if (taskService.findTaskByIdAndByStudentId(taskAnswerDto.getStudentId(), taskAnswerDto.getTaskId()) == null) {
            throw new RuntimeException("It is impossible to send an answer for this task");
        }
        TaskAnswer taskAnswer = new TaskAnswer();
        taskAnswer.setComment(taskAnswerDto.getComment());

        taskAnswer.setStudent(studentService.findStudentById(taskAnswerDto.getStudentId()));
        taskAnswer.setTask(taskService.findTaskById(taskAnswerDto.getTaskId()));

        taskService.findTaskById(taskAnswerDto.getTaskId()).getTaskAnswerSet().add(taskAnswer);

        return taskAnswerRepository.save(taskAnswer);
    }

    @Override
    public void update(long answerId, TaskAnswerDto taskAnswerDto) {
        TaskAnswer taskAnswer = findById(answerId);
        taskAnswer.setComment(taskAnswerDto.getComment());
        taskAnswerRepository.save(taskAnswer);
    }

    @Override
    public void delete(long answerId) {
        if (taskAnswerRepository.existsById(answerId)) {
            taskAnswerRepository.deleteById(answerId);
        } else {
            throw new RuntimeException("Answer with id " + answerId + " was not found");
        }

    }

    @Override
    public TaskAnswer findById(long answerId) {
        return taskAnswerRepository.findById(answerId)
                .orElseThrow(() -> new RuntimeException("Task answer with id " + answerId + " was not found"));
    }
}
