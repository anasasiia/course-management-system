package com.example.app.controller;

import com.example.app.dto.MessageResponse;
import com.example.app.service.GroupService;
import com.example.app.service.SubjectService;
import com.example.app.dto.SubjectDto;
import com.example.app.service.TeacherService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/subject")
@RequiredArgsConstructor
@Slf4j
public class SubjectController {

    private final SubjectService subjectService;
    private final TeacherService teacherService;
    private final GroupService groupService;

    @GetMapping("/{subjectId}")
    public ResponseEntity<?> getSubjectById(@PathVariable long subjectId) {
        return ResponseEntity.ok().body(subjectService.findSubjectById(subjectId));
    }

    @GetMapping("/group/{groupId}/{page}")
    public ResponseEntity<?> getAllSubjectsByGroupId(@PathVariable long groupId, @PathVariable int page) {
        Pageable pageable = PageRequest.of(page, 5);
        return ResponseEntity.ok().body(subjectService.findAllSubjectsByGroupId(groupId, pageable).getContent());
    }

    @GetMapping("/teacher/{teacherId}/{page}")
    public ResponseEntity<?> findSubjectByNameAndByTeacherId(@PathVariable long teacherId,
                                                             @RequestParam("name") String name,
                                                             @PathVariable int page) {
        Pageable pageable = PageRequest.of(page, 5);
        return ResponseEntity.ok().body(subjectService.findSubjectByNameAndByTeacherId(teacherId, name, pageable).getContent());
    }

    @GetMapping("/teacher/all/{teacherId}/{page}")
    public ResponseEntity<?> findAllSubjectsByTeacherId(@PathVariable long teacherId, @PathVariable int page) {
        Pageable pageable = PageRequest.of(page, 5);
        return ResponseEntity.ok().body(subjectService.findAllSubjectsByTeacherId(teacherId, pageable));
    }

    @PostMapping("/create")
    public ResponseEntity<?> createSubject(@RequestBody SubjectDto subjectDto) {
        try {
            subjectService.create(subjectDto);
            return ResponseEntity.ok().body(new MessageResponse("Subject with a name " +
                    subjectDto.getName() + " was created"));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(new MessageResponse("Subject with a name " + subjectDto.getName() +
                    " was not created. Error " + e.getLocalizedMessage()));
        }
    }

    @DeleteMapping("/{subjectId}")
    public ResponseEntity<?> deleteSubject(@PathVariable long subjectId) {
        subjectService.delete(subjectId);
        return ResponseEntity.ok().body(new MessageResponse("Subject with id " +
                subjectId + " was deleted"));
    }

    @PutMapping("update/{subjectId}/addTeacher/{teacherId}")
    public ResponseEntity<?> addTeacherToSubject(@PathVariable long subjectId, @PathVariable long teacherId) {
        try {
            subjectService.addTeacherToSubject(subjectId, teacherId);
            return ResponseEntity.ok().body(new MessageResponse("Teacher with id " + teacherId +
                    " was added to subject with id " + subjectId));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(new MessageResponse("Teacher with id " + teacherId +
                    " was not added to subject with id " + subjectId + ". Error: " + e.getLocalizedMessage()));
        }
    }

    @PutMapping("update/{subjectId}/addGroup/{groupId}")
    public ResponseEntity<?> addGroupToSubject(@PathVariable long subjectId, @PathVariable long groupId) {
        try {
            subjectService.addGroupToSubject(subjectId, groupId);
            return ResponseEntity.ok().body(new MessageResponse("Group with id " + groupId +
                    " was added to subject with id " + subjectId));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(new MessageResponse("Group with id " + groupId +
                    " was not added to subject with id " + subjectId + ". Error: " + e.getLocalizedMessage()));
        }
    }

    @PutMapping("update/{subjectId}/deleteTeacher/{teacherId}")
    public ResponseEntity<?> deleteTeacherFromSubject(@PathVariable long subjectId, @PathVariable long teacherId) {
        try {
            subjectService.deleteTeacherToSubject(subjectId, teacherId);
            return ResponseEntity.ok().body(new MessageResponse("Teacher with id " + teacherId +
                    " was deleted from subject with id " + subjectId));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(new MessageResponse("Teacher with id " + teacherId +
                    " was not deleted from subject with id " + subjectId + ". Error: " + e.getLocalizedMessage()));
        }
    }

    @PutMapping("update/{subjectId}/deleteGroup/{groupId}")
    public ResponseEntity<?> deleteGroupToSubject(@PathVariable long subjectId, @PathVariable long groupId) {
        try {
            subjectService.deleteGroupToSubject(subjectId, groupId);
            return ResponseEntity.ok().body(new MessageResponse("Group with id " + groupId +
                    " was deleted from subject with id " + subjectId));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(new MessageResponse("Group with id " + groupId +
                    " was not deleted from subject with id " + subjectId + ". Error: " + e.getLocalizedMessage()));
        }
    }
}
