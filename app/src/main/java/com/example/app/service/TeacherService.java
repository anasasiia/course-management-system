package com.example.app.service;

import com.example.app.dto.TeacherDto;
import com.example.app.model.Teacher;

public interface TeacherService {
    Teacher findTeacherById(long id);
    void createTeacher(TeacherDto teacherDto);
    void updateTeacher(long id, TeacherDto teacherDto);
    void deleteTeacher(long id);
}
