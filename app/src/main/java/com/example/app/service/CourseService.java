package com.example.app.service;

import com.example.app.dto.CourseDto;
import com.example.app.model.Course;

public interface CourseService {
    Course createCourse(CourseDto courseDto);
    Course updateCourse(long id, CourseDto courseDto);
}
