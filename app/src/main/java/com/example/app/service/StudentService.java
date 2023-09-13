package com.example.app.service;

import com.example.app.dto.StudentDto;
import com.example.app.model.Student;

public interface StudentService {
    Student findStudentById(long id);
    void create(StudentDto studentDto);
    void update(long id, StudentDto studentDto);
    void delete(long id);
}
