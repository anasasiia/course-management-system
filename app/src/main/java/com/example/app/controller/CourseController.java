package com.example.app.controller;

import com.example.app.dto.CourseDto;
import com.example.app.model.Course;
import com.example.app.repository.CourseRepository;
import com.example.app.service.CourseService;
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

import static com.example.app.controller.CourseController.COURSE_CONTROLLER_PATH;

@RestController
@AllArgsConstructor
@RequestMapping(COURSE_CONTROLLER_PATH)
public class CourseController {
    public static final String COURSE_CONTROLLER_PATH = "/courses";
    public static final String ID = "/id";

    private final CourseRepository courseRepository;
    private final CourseService courseService;

    @GetMapping
    public List<Course> getAll() {
        return courseRepository.findAll().stream().toList();
    }

    @PostMapping("new")
    public Course createCourse(@RequestBody @Valid final CourseDto courseDto) {
        return courseService.createCourse(courseDto);
    }

    @PutMapping(ID)
    public Course updateCourse(@PathVariable final long id, @RequestBody @Valid final CourseDto courseDto) {
        return courseService.updateCourse(id, courseDto);
    }

    @GetMapping(ID)
    public Course getCourseById(@PathVariable final long id) {
        return courseRepository.getById(id);
    }

    @DeleteMapping(ID)
    public void deleteCourse(@PathVariable final long id) {
        courseRepository.deleteById(id);
    }
}
