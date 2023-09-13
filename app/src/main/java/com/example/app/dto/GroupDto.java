package com.example.app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@AllArgsConstructor
@Data
public class GroupDto {
    private String name;
    private List<Long> studentsId;
}
