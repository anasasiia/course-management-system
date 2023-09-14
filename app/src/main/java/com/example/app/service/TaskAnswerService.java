package com.example.app.service;

import com.example.app.dto.TaskAnswerDto;
import com.example.app.model.TaskAnswer;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface TaskAnswerService {
    TaskAnswer findByStudentIdAndByTaskId(long studentId, long taskId);
    List<TaskAnswer> findByTeacherIdAndTaskId(long teacherId, long taskId);
    void addFileToAnswer(long answerId, MultipartFile[] files);
    void deleteFileFromAnswer(long answerId, String fileUri);
    TaskAnswer create(TaskAnswerDto taskAnswerDto);
    void update(long answerId, TaskAnswerDto taskAnswerDto);
    void delete(long answerId);
    TaskAnswer findById(long answerId);
}
