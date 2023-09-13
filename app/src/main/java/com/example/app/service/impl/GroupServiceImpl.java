package com.example.app.service.impl;

import com.example.app.dto.GroupDto;
import com.example.app.model.Group;
import com.example.app.repository.GroupRepository;
import com.example.app.service.GroupService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class GroupServiceImpl implements GroupService {
    private GroupRepository groupRepository;
    private StudentService studentService;
    @Override
    public Group findGroupById(long id) {
        return groupRepository.findById(id).orElseThrow(() -> new RuntimeException("Group with id "
                + id + " is not found"));
    }

    @Override
    public void createGroup(GroupDto groupDto) {
        Group group = new Group();
        group.setName(groupDto.getName());
        groupDto.getStudentsId().forEach(id -> group.getStudents().add(studentService.findStudentById(id)));
        groupRepository.save(group);
    }

    @Override
    public void deleteGroupById(long groupId) {
        if (groupRepository.existById(groupId)) {
            groupRepository.deleteById(groupId);
        } else {
            throw new RuntimeException("Group with id " + groupId + "does not exist. It is impossible to delete");
        }
    }

    @Override
    public void addStudentToGroup(long groupId, long studentId) {
        Group group = findGroupById(groupId);
        group.getStudents().add(studentService.findStudentById(studentId));
        groupRepository.save(group);
    }

    @Override
    public void deleteStudentFromGroup(long groupId, long studentId) {
        Group group = findGroupById(groupId);
        group.getStudents().remove(studentService.findStudentById(studentId));
        groupRepository.save(group);
    }
}
