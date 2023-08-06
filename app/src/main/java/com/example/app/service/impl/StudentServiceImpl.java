package com.example.app.service.impl;

import com.example.app.dto.StudentDto;
import com.example.app.model.Student;
import com.example.app.repository.StudentRepository;
import com.example.app.service.StudentService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@AllArgsConstructor
public class StudentServiceImpl implements StudentService {
    private final StudentRepository studentRepository;
    @Override
    public Student createStudent(final StudentDto dto) {
        final Student student = new Student();
        student.setUserName(dto.getUserName());
        student.setFirstName(dto.getFirstName());
        student.setLastName(dto.getLastName());
        student.setPassword(dto.getPassword());
        return studentRepository.save(student);
    }

    @Override
    public Student updateStudent(final long id, StudentDto dto) {
        Student student = studentRepository.getById(id);
        student.setUserName(dto.getUserName());
        student.setFirstName(dto.getFirstName());
        student.setLastName(dto.getLastName());
        student.setPassword(dto.getPassword());
        return studentRepository.save(student);
    }
}
