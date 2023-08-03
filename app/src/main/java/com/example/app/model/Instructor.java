package com.example.app.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "instructors")
@Entity
public class Instructor {
    @Id
    private long id;

    private String userName;

    private String password;

    private String firstName;

    private String lastName;

    @ManyToMany(mappedBy = "instructorList")
    private List<Course> courseList;
}