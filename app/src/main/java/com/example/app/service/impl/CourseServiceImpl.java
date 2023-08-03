package com.example.app.service.impl;

import com.example.app.dto.CourseDto;
import com.example.app.model.Course;
import com.example.app.repository.CourseRepository;
import com.example.app.service.CourseService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@AllArgsConstructor
public class CourseServiceImpl implements CourseService {
    private final CourseRepository courseRepository;
    @Override
    public Course createCourse(final CourseDto courseDto) {
        final Course course = new Course();
        course.setName(courseDto.getName());
        course.setInstructorList(courseDto.getInstructorList());
        course.setStudentList(courseDto.getStudentList());
        return courseRepository.save(course);
    }

    @Override
    public Course updateCourse(final long id, final CourseDto courseDto) {
        final Course course = courseRepository.getById(id);
        course.setName(courseDto.getName());
        course.setInstructorList(courseDto.getInstructorList());
        course.setStudentList(courseDto.getStudentList());
        return courseRepository.save(course);
    }
}
