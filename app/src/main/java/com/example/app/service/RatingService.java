package com.example.app.service;

import com.example.app.dto.RatingDtoToCreate;
import com.example.app.dto.RatingDtoToUpdate;
import com.example.app.model.Rating;

import java.util.List;

public interface RatingService {
    Rating findByTaskIdAndStudentId(long taskId, long studentId);
    List<Rating> findByTaskIdAndTeacherId(long taskId, long teacherId);
    List<Rating> findBySubjectIdAndStudentId(long subjectId, long studentId);
    Rating findByIdAndTeacherId(long ratingId, long teacherId);
    Rating create(RatingDtoToCreate ratingDtoToCreate);
    void update(long ratingId, long teacherId, RatingDtoToUpdate ratingDtoToUpdate);
}
