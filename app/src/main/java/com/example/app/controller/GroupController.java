package com.example.app.controller;

import com.example.app.dto.GroupDto;
import com.example.app.dto.MessageResponse;
import com.example.app.service.GroupService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/group")
@RestController
@AllArgsConstructor
@Slf4j
public class GroupController {

    private final GroupService groupService;

    @GetMapping("/{groupId}")
    public ResponseEntity<?> getGroupById(@PathVariable long groupId) {
        return ResponseEntity.ok().body(groupService.findGroupById(groupId));
    }

    @GetMapping("/{groupId}/students")
    public ResponseEntity<?> getStudentsByGroupId(@PathVariable long groupId) {
        return ResponseEntity.ok().body(groupService.findGroupById(groupId).getStudents());
    }

    @PostMapping("/create")
    public ResponseEntity<?> createGroup(@RequestBody GroupDto groupDto) {
        try {
            groupService.createGroup(groupDto);
            return ResponseEntity.ok().body(new MessageResponse("Group was created with a name " + groupDto.getName()));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(new MessageResponse("Group with a name "
                    + groupDto.getName() + " was not created. Error: " + e.getLocalizedMessage()));
        }
    }

    @DeleteMapping("/{groupId}")
    public ResponseEntity<?> deleteGroup(@PathVariable long groupId) {
        groupService.deleteGroupById(groupId);
        return ResponseEntity.ok().body(new MessageResponse("Group with id " + groupId + " was deleted"));
    }

    @PutMapping("/update/{groupId}/{studentId}")
    public ResponseEntity<?> addStudentToGroup(@PathVariable long groupId, @PathVariable long studentId) {
        try {
            groupService.addStudentToGroup(groupId, studentId);
            return ResponseEntity.ok().body(new MessageResponse("Student with id " + studentId
                    + " was added to the group with id " + groupId));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(new MessageResponse("Student with id " + studentId
                    + " was not added to the group with id" + groupId + ". Error " + e.getLocalizedMessage()));
        }
    }

    @PutMapping("/delete/{groupId}/{studentId}")
    public ResponseEntity<?> deleteStudentFromGroup(@PathVariable long groupId, @PathVariable long studentId) {
        groupService.deleteStudentFromGroup(groupId, studentId);
        return ResponseEntity.ok().body(new MessageResponse("Student with id " + studentId
                + " was deleted from the group with id " + groupId));
    }

}
