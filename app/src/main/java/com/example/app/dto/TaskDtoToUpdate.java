package com.example.app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class TaskDtoToUpdate {
    private String name;
    private String description;
    private Date deadLine;
    private long teacherId;
}
