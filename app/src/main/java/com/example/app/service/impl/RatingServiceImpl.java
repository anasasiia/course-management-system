package com.example.app.service.impl;

import com.example.app.dto.RatingDtoToCreate;
import com.example.app.dto.RatingDtoToUpdate;
import com.example.app.model.Rating;
import com.example.app.model.Student;
import com.example.app.model.Task;
import com.example.app.repository.RatingRepository;
import com.example.app.service.RatingService;
import com.example.app.service.StudentService;
import com.example.app.service.SubjectService;
import com.example.app.service.TaskService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class RatingServiceImpl implements RatingService {
    private final RatingRepository ratingRepository;
    private final TaskService taskService;
    private final SubjectService subjectService;
    private final StudentService studentService;
    @Override
    public Rating findByTaskIdAndStudentId(long taskId, long studentId) {
        return ratingRepository.findByTaskIdAndStudentId(taskId, studentId)
                .orElseThrow(() -> new RuntimeException("Rating for task with id=" + taskId + " was not found"));
    }

    @Override
    public List<Rating> findByTaskIdAndTeacherId(long taskId, long teacherId) {
        return ratingRepository.findByTaskIdAndTeacherId(taskId, teacherId);
    }

    @Override
    public List<Rating> findBySubjectIdAndStudentId(long subjectId, long studentId) {
        return ratingRepository.findBySubjectIdAndStudentId(subjectId, studentId);
    }

    @Override
    public Rating findByIdAndTeacherId(long ratingId, long teacherId) {
        return ratingRepository.findByIdAndTeacherId(ratingId, teacherId)
                .orElseThrow(() -> new RuntimeException("Rating with id=" + ratingId + " was not found"));
    }

    @Override
    public Rating create(RatingDtoToCreate ratingDtoToCreate) {
        Task task = taskService.findTaskById(ratingDtoToCreate.getTaskId());
        Student student = studentService.findStudentById(ratingDtoToCreate.getStudentId());

        if (!task.getTeacher().getGroupList().contains(student.getGroup())) {
            throw new RuntimeException("It is not allowed to rate this task. Group is assigned to another teacher");
        }

        Rating rating = new Rating();
        rating.setComment(ratingDtoToCreate.getComment());
        rating.setMark(ratingDtoToCreate.getMark());
        rating.setTask(task);
        rating.setSubject(subjectService.findSubjectById(ratingDtoToCreate.getSubjectId()));
        rating.setStudent(student);

        studentService.findStudentById(ratingDtoToCreate.getStudentId()).getRatingList().add(rating);
        return ratingRepository.save(rating);
    }

    @Override
    public void update(long ratingId, long teacherId, RatingDtoToUpdate ratingDtoToUpdate) {
        Rating rating = findByIdAndTeacherId(ratingId, teacherId);
        rating.setMark(ratingDtoToUpdate.getMark());
        rating.setComment(ratingDtoToUpdate.getComment());
        ratingRepository.save(rating);
    }
}
