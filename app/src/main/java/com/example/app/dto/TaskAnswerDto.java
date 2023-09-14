package com.example.app.dto;

import lombok.Data;

@Data
public class TaskAnswerDto {
    private String comment;
    private long studentId;
    private long taskId;
}
