package com.example.app.service;

import com.example.app.dto.StudentDto;
import com.example.app.model.Student;

public interface StudentService {

    Student createStudent(StudentDto studentDto);

    Student updateStudent(long id, StudentDto studentDto);
}
