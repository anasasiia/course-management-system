package com.example.app.service.impl;

import com.example.app.dto.StudentDto;
import com.example.app.model.Student;
import com.example.app.repository.StudentRepository;
import com.example.app.service.StudentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;

    @Override
    public Student findStudentById(long id) {
        return studentRepository.findStudentById(id).orElseThrow(() ->
                new RuntimeException("Student with id " + id  + " is not found"));
    }

    @Override
    public void create(StudentDto studentDto) {
        Student student = new Student();
        student.setFirstName(studentDto.getFirstName());
        student.setLastName(studentDto.getLastName());
        student.setEmail(studentDto.getEmail());
        student.setPassword(studentDto.getPassword());
        student.setPhoneNumber(studentDto.getPhoneNumber());
        studentRepository.save(student);
    }

    @Override
    public void update(long id, StudentDto studentDto) {
        Student student = studentRepository.findStudentById(id).get();
        student.setFirstName(studentDto.getFirstName());
        student.setLastName(studentDto.getLastName());
        student.setEmail(studentDto.getEmail());
        student.setPassword(studentDto.getPassword());
        student.setPhoneNumber(studentDto.getPhoneNumber());
        studentRepository.save(student);
    }

    @Override
    public void delete(long id) {
        if (studentRepository.existsById(id)) {
            Student student = studentRepository.findStudentById(id).get();
            studentRepository.delete(student);
        } else {
            throw new RuntimeException("Student with id " + id + "does not exist. It is impossible to delete");
        }
    }
}
