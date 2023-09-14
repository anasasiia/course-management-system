package com.example.app.dto;

import lombok.Data;

@Data
public class RatingDtoToCreate {
    private int mark;

    private String comment;

    private long taskId;

    private long subjectId;

    private long studentId;
}
