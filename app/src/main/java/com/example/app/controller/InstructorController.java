package com.example.app.controller;

import com.example.app.dto.InstructorDto;
import com.example.app.model.Instructor;
import com.example.app.repository.InstructorRepository;
import com.example.app.service.InstructorService;
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

import static com.example.app.controller.InstructorController.INSTRUCTOR_CONTROLLER_PATH;

@RestController
@AllArgsConstructor
@RequestMapping(INSTRUCTOR_CONTROLLER_PATH)
public class InstructorController {
    public static final String INSTRUCTOR_CONTROLLER_PATH = "/instructors";

    public static final String ID = "/id";

    private InstructorRepository instructorRepository;
    private InstructorService instructorService;

    @GetMapping
    public List<Instructor> getAll() {
        return instructorRepository.findAll().stream().toList();
    }

    @GetMapping(ID)
    public Instructor getInstructorById(@PathVariable final long id) {
        return instructorRepository.getById(id);
    }

    @PostMapping
    public Instructor createInstructor(@RequestBody @Valid final InstructorDto instructorDto) {
        return instructorService.createInstructor(instructorDto);
    }

    @PutMapping(ID)
    public Instructor updateInstructor(@PathVariable final long id,
                                       @RequestBody @Valid final InstructorDto instructorDto) {
        return instructorService.updateInstructor(id, instructorDto);
    }

    @DeleteMapping(ID)
    public void deleteInstructor(@PathVariable final long id) {
        instructorRepository.deleteById(id);
    }
}
