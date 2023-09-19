package com.example.app.controller;

import com.example.app.dto.MessageResponse;
import com.example.app.dto.TeacherDto;
import com.example.app.service.TeacherService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/teacher")
@RequiredArgsConstructor
@Slf4j
public class TeacherController {

    private final TeacherService teacherService;

    @RabbitListener(queues = "teacherQueue")
    public void TeacherListener(String message) {
        log.info("Teacher received a message: " + message);
    }

    @GetMapping("/{teacherId}")
    public ResponseEntity<?> getTeacherById(@PathVariable long teacherId) {
        return ResponseEntity.ok().body(teacherService.findTeacherById(teacherId));
    }

    @GetMapping("/{teacherId}/groups")
    public ResponseEntity<?> getGroupsByTeacherId(@PathVariable long teacherId) {
        return ResponseEntity.ok().body(teacherService.findTeacherById(teacherId).getGroups());
    }

    @PostMapping("/create")
    public ResponseEntity<?> createTeacher(@RequestBody TeacherDto teacherDto) {
        try {
            teacherService.createTeacher(teacherDto);
            return ResponseEntity.ok().body(new MessageResponse("Teacher was created with a name " +
                    teacherDto.getFirstName() + " " + teacherDto.getLastName()));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Teacher with a name " + teacherDto.getFirstName() + " " +
                    teacherDto.getLastName() + " was not created. Error " + e.getLocalizedMessage());
        }
    }

    @PutMapping("/update/{teacherId}")
    public ResponseEntity<?> updateTeacher(@PathVariable long teacherId, @RequestBody TeacherDto teacherDto) {
        try {
            teacherService.updateTeacher(teacherId, teacherDto);
            return ResponseEntity.ok().body(new MessageResponse("Teacher was updated with a name " +
                    teacherDto.getFirstName() + " " + teacherDto.getLastName()));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Teacher with a name " + teacherDto.getFirstName() + " " +
                    teacherDto.getLastName() + " was not updated. Error " + e.getLocalizedMessage());
        }
    }

    @DeleteMapping("{teacherId}")
    public ResponseEntity<?> deleteTeacher(@PathVariable long teacherId) {
        teacherService.deleteTeacher(teacherId);
        return ResponseEntity.ok().body(new MessageResponse("Teacher with id " + teacherId + " was deleted"));
    }
}
