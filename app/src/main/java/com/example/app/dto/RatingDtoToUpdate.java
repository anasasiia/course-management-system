package com.example.app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RatingDtoToUpdate {
    private int mark;
    private String comment;
}
