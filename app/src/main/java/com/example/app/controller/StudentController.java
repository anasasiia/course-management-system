package com.example.app.controller;

import com.example.app.dto.StudentDto;
import com.example.app.model.Student;
import com.example.app.repository.StudentRepository;
import com.example.app.service.StudentService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

import static com.example.app.controller.StudentController.STUDENT_CONTROLLER_PATH;

@RestController
@AllArgsConstructor
@RequestMapping(STUDENT_CONTROLLER_PATH)
public class StudentController {
    public static final String STUDENT_CONTROLLER_PATH = "/students";

    public static final String ID = "/id";

    private StudentRepository studentRepository;
    private StudentService studentService;

    @GetMapping()
    public List<Student> getAll() {
        return studentRepository.findAll().stream().toList();
    }

    @GetMapping(ID)
    public Student getStudentById(final @Valid @PathVariable long id) {
        return studentRepository.getById(id);
    }

    @PostMapping()
    public Student createStudent(final @Valid @RequestBody StudentDto studentDto) {
        return studentService.createStudent(studentDto);
    }

    @PutMapping(ID)
    public Student updateStudent(final @PathVariable long id, final @Valid @RequestBody StudentDto studentDto) {
        return studentService.updateStudent(id, studentDto);
    }

    @DeleteMapping(ID)
    public void deleteStudent(final @Valid @PathVariable long id) {
        studentRepository.deleteById(id);
    }
}
