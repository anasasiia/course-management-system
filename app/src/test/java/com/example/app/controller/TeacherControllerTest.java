package com.example.app.controller;

import com.example.app.dto.GroupDto;
import com.example.app.dto.MessageResponse;
import com.example.app.dto.StudentDto;
import com.example.app.dto.TeacherDto;
import com.example.app.model.Group;
import com.example.app.model.Student;
import com.example.app.model.Teacher;
import com.example.app.repository.StudentRepository;
import com.example.app.repository.TeacherRepository;
import com.example.app.utils.TestUtils;
import com.fasterxml.jackson.core.type.TypeReference;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.example.app.utils.TestUtils.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = RANDOM_PORT)
public class TeacherControllerTest {
    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private TestUtils utils;

    @AfterEach
    public final void clear() {
        utils.tearDown();
    }

    @Test
    public void createTeacherTest() throws Exception {
        final TeacherDto teacherDto = utils.getTestTeacherDto();

        final var request = post("/teacher/create")
                .content(asJson(teacherDto))
                .contentType(APPLICATION_JSON);

        utils.perform(request).andExpect(status().isOk());
        assertEquals(1, teacherRepository.count());
    }

    @Test
    public void getTeacherByIdTest() throws Exception {
        utils.createDefaultTeacher();

        final Teacher expectedTeacher = teacherRepository.findAll().get(0);

        final var response = utils.perform(
                        get("/teacher/" + expectedTeacher.getId()))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse();

        Teacher teacher = fromJson(response.getContentAsString(), new TypeReference<Teacher>() {
        });

        assertEquals(teacher.getEmail(), expectedTeacher.getEmail());
        assertEquals(teacher.getFirstName(), expectedTeacher.getFirstName());
        assertEquals(teacher.getLastName(), expectedTeacher.getLastName());
        assertEquals(teacher.getPhoneNumber(), expectedTeacher.getPhoneNumber());
        assertEquals(teacher.getPassword(), expectedTeacher.getPassword());
        assertEquals(teacher.getAcademicDegree(), expectedTeacher.getAcademicDegree());
    }

    @Test
    public void deleteTeacherTest() throws Exception {
        utils.createDefaultTeacher();

        final long teacherId = teacherRepository.findAll().get(0).getId();

        utils.perform(delete("/teacher/" + teacherId))
                .andExpect(status().isOk());

        assertEquals(teacherRepository.count(), 0);
    }

    @Test
    public void updateTeacherTest() throws Exception {
        utils.createDefaultTeacher();

        final long teacherId = teacherRepository.findAll().get(0).getId();
        final TeacherDto teacherDto = new TeacherDto(
                "new firstName",
                "new lastName",
                TEST_EMAIL_2,
                "newPwd",
                "+79153335522",
                "Phd"
        );

        final var response = utils.perform(
                        put("/teacher/update/" + teacherId)
                                .content(asJson(teacherDto))
                                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse();

        final MessageResponse messageResponse = fromJson(response.getContentAsString(), new TypeReference<MessageResponse>() {});

        assertTrue(teacherRepository.existsById(teacherId));
        assertEquals("Teacher was updated with a name " + teacherDto.getFirstName() + " " + teacherDto.getLastName(), messageResponse.getMessage());
    }

    @Test
    public void getGroupsByTeacherId() throws Exception {
        utils.createDefaultTeacher();
        utils.createDefaultStudent();

        final long studentId = studentRepository.findAll().get(0).getId();

        GroupDto groupDto = new GroupDto(
                "WEB-1",
                new ArrayList<>(Collections.singletonList(studentId))
        );

        utils.perform(post("/group/create")
                .content(asJson(groupDto))
                .contentType(APPLICATION_JSON));

        final long teacherId = teacherRepository.findAll().get(0).getId();

        final var response = utils.perform(get("/teacher/" + teacherId + "/groups"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse();

        List<Group> groups = teacherRepository.findAll().get(0).getGroups();

        assertEquals(1, groups.size());
    }
}
