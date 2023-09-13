package com.example.app.service.impl;

import com.example.app.dto.TeacherDto;
import com.example.app.model.Teacher;
import com.example.app.repository.TeacherRepository;
import com.example.app.service.TeacherService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class TeacherServiceImpl implements TeacherService {

    private final TeacherRepository teacherRepository;

    @Override
    public Teacher findTeacherById(long id) {
        return teacherRepository.findById(id).orElseThrow(() -> new RuntimeException("Teacher with id " + id +
                " was not found"));
    }

    @Override
    public void createTeacher(TeacherDto teacherDto) {
        Teacher teacher = new Teacher();
        teacher.setFirstName(teacherDto.getFirstName());
        teacher.setLastName(teacherDto.getLastName());
        teacher.setEmail(teacherDto.getEmail());
        teacher.setPassword(teacherDto.getPassword());
        teacher.setPhoneNumber(teacherDto.getPhoneNumber());
        teacher.setAcademicDegree(teacherDto.getAcademicDegree());
        teacherRepository.save(teacher);
    }

    @Override
    public void updateTeacher(long id, TeacherDto teacherDto) {
        Teacher teacher = teacherRepository.findById(id).get();
        teacher.setFirstName(teacherDto.getFirstName());
        teacher.setLastName(teacherDto.getLastName());
        teacher.setEmail(teacherDto.getEmail());
        teacher.setPassword(teacherDto.getPassword());
        teacher.setPhoneNumber(teacherDto.getPhoneNumber());
        teacher.setAcademicDegree(teacherDto.getAcademicDegree());
        teacherRepository.save(teacher);
    }

    @Override
    public void deleteTeacher(long id) {
        teacherRepository.deleteById(id);
    }
}
