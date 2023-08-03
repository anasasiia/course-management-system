package com.example.app.dto;

import com.example.app.model.Instructor;
import com.example.app.model.Student;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CourseDto {
    private String name;

    private List<Instructor> instructorList;

    private List<Student> studentList;
}
