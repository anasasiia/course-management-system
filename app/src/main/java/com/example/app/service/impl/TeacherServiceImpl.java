package com.example.app.service.impl;

import com.example.app.dto.TeacherDto;
import com.example.app.model.Group;
import com.example.app.model.Teacher;
import com.example.app.repository.TeacherRepository;
import com.example.app.service.TeacherService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TeacherServiceImpl implements TeacherService {
    private final TeacherRepository teacherRepository;
//    @Override
//    public Page<Group> findGroupsByTeacherId(long id, Pageable pageable) {
//        Teacher teacher = teacherRepository.findById(id).get();
//        return teacher.getGroupList();
//    }

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
