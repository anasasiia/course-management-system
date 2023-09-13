package com.example.app.dto;

import com.example.app.model.Group;
import com.example.app.model.Teacher;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskDto {
    private String name;

    private String description;

    private Date startLine;

    private Date deadLine;

    private Teacher teacher;

    private Group group;
}
