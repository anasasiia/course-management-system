package com.example.app.dto;

import com.example.app.model.User;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CourseDto {
    private String name;

    private List<User> instructorList;

    private List<User> studentList;
}
