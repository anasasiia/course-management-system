package com.example.app.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "courses")
@Entity
public class Course {
    @Id
    private long id;

    private String name;

    private List<Instructor> instructorList;

    private List<Student> studentList;
}
