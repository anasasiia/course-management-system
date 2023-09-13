package com.example.app.service;

import com.example.app.dto.GroupDto;
import com.example.app.model.Group;

public interface GroupService {
    Group findGroupById(long id);
    void createGroup(GroupDto groupDto);

    void deleteGroupById(long groupId);

    void addStudentToGroup(long groupId, long studentId);

    void deleteStudentFromGroup(long groupId, long studentId);

    Group findGroupByIdAndByTeacherId(long teacherId, long groupId);
}
