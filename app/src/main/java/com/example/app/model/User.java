package com.example.app.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.List;

@Entity
@Data
@Table(name = "users")
public class User {
    @Id
    @Column(name = "id")
    private long id;

    @Column(name = "userName")
    private String userName;

    @Column(name = "password")
    private String password;

    @Column(name = "firstName")
    private String firstName;

    @Column(name = "lastName")
    private String lastName;

    @ManyToMany(mappedBy = "userList")
    private List<Course> courseList;

    @ManyToMany
    @JoinTable(
            name = "users_roles",
            joinColumns = { @JoinColumn(name = "student_id")},
            inverseJoinColumns = { @JoinColumn(name = "role_id")}
    )
    private List<Role> roleList;
}
