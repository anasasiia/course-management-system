package com.example.app.controller;

import com.example.app.dto.CourseDto;
import com.example.app.model.Course;
import com.example.app.model.User;
import com.example.app.repository.CourseRepository;
import com.example.app.repository.UserRepository;
import com.example.app.service.CourseService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@AllArgsConstructor
@RequestMapping
public class CourseController {
    public static final String ID = "/id";

    private final CourseRepository courseRepository;
    private final UserRepository userRepository;
    private final CourseService courseService;


    @GetMapping("/courses")
    public String getCoursesOfUser(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getAuthorities().contains("ROLE_ADMIN")) {
            model.addAttribute("courses", courseRepository.findAll().stream().toList());
            return "coursesAdmin";
        }

        User user = userRepository.findByUserName(authentication.getName());
        model.addAttribute("courses", user.getCourseList());
        return "courses";
    }

    @PostMapping("/courses/new")
    public Course createCourse(@RequestBody @Valid final CourseDto courseDto) {
        return courseService.createCourse(courseDto);
    }

    @PutMapping("/courses" + ID)
    public Course updateCourse(@PathVariable final long id, @RequestBody @Valid final CourseDto courseDto) {
        return courseService.updateCourse(id, courseDto);
    }

    @GetMapping("/courses" + ID)
    public Course getCourseById(@PathVariable final long id) {
        return courseRepository.getById(id);
    }

    @DeleteMapping("/courses" + ID)
    public void deleteCourse(@PathVariable final long id) {
        courseRepository.deleteById(id);
    }
}
