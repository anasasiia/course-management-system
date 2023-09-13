package com.example.app.controller;

import com.example.app.dto.MessageResponse;
import com.example.app.dto.StudentDto;
import com.example.app.service.StudentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/student")
@Slf4j
public class StudentController {

    private final StudentService studentService;

    @RabbitListener(queues = "studentQueue")
    public void StudentListener(String message) {
        log.info("Student received a message: " + message);
    }

    @GetMapping("/{studentId}")
    public ResponseEntity<?> getStudentById(@PathVariable long studentId) {
        return ResponseEntity.ok().body(studentService.findStudentById(studentId));
    }

    @PostMapping("/create")
    public ResponseEntity<?> createStudent(@RequestBody StudentDto studentDto) {
        try {
            studentService.create(studentDto);
            return ResponseEntity.ok().body(new MessageResponse("Student was created with a name " +
                    studentDto.getFirstName() + " " + studentDto.getLastName()));
        } catch (RuntimeException e) {
            log.error("Student with a name " + studentDto.getFirstName() + " " + studentDto.getLastName() +
                    " was not created. Error " + e.getLocalizedMessage());
            return ResponseEntity.badRequest().body("Student with a name " + studentDto.getFirstName() + " " + studentDto.getLastName() +
                    " was not created. Error " + e.getLocalizedMessage());
        }
    }

    @PutMapping("/update/{studentId}")
    public ResponseEntity<?> updateStudent(@PathVariable long studentId, @RequestBody StudentDto studentDto) {
        try {
            studentService.update(studentId, studentDto);
            return ResponseEntity.ok().body(new MessageResponse("Student with a name " +
                    studentDto.getFirstName() + " " + studentDto.getLastName()) + " was updated");
        } catch (RuntimeException e) {
            log.error("Student with id " + studentId + " was not updated. Error " + e.getLocalizedMessage());
            return ResponseEntity.badRequest().body("Student with id " + studentId + " was not updated. Error " +
                    e.getLocalizedMessage());
        }
    }

    @DeleteMapping("/{studentId}")
    public ResponseEntity<?> deleteStudent(@PathVariable long studentId) {
        studentService.delete(studentId);
        return ResponseEntity.ok().body("Student with id " + studentId + " was deleted");
    }
}
