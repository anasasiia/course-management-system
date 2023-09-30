package com.example.app.controller;

import com.example.app.dto.MessageResponse;
import com.example.app.dto.StudentDto;
import com.example.app.model.Student;
import com.example.app.repository.StudentRepository;
import com.example.app.utils.TestUtils;
import com.fasterxml.jackson.core.type.TypeReference;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import static com.example.app.utils.TestUtils.TEST_EMAIL_2;
import static com.example.app.utils.TestUtils.asJson;
import static com.example.app.utils.TestUtils.fromJson;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = RANDOM_PORT)
public class StudentControllerTest {
    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private TestUtils utils;

    @AfterEach
    public final void clear() {
        utils.tearDown();
    }

    @Test
    public void createStudentTest() throws Exception {
        final StudentDto studentDto = new StudentDto(
                "Simon",
                "Moore",
                "email3@email.com",
                "pwd",
                "+79083445566"
        );

        final var request = post("/student/create")
                .content(asJson(studentDto))
                .contentType(APPLICATION_JSON);

        utils.perform(request).andExpect(status().isOk());
        assertEquals(1, studentRepository.count());
    }

    @Test
    public void getStudentByIdTest() throws Exception {
        utils.createDefaultStudent();

        final Student expectedStudent = studentRepository.findAll().get(0);

        final var response = utils.perform(
                get("/student/" + expectedStudent.getId()))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse();

        Student student = fromJson(response.getContentAsString(), new TypeReference<Student>() {
        });

        assertEquals(student.getEmail(), expectedStudent.getEmail());
        assertEquals(student.getFirstName(), expectedStudent.getFirstName());
        assertEquals(student.getLastName(), expectedStudent.getLastName());
        assertEquals(student.getPhoneNumber(), expectedStudent.getPhoneNumber());
        assertEquals(student.getPassword(), expectedStudent.getPassword());
    }

    @Test
    public void deleteStudentTest() throws Exception {
        utils.createDefaultStudent();

        final long studentId = studentRepository.findAll().get(0).getId();

        utils.perform(delete("/student/" + studentId))
                .andExpect(status().isOk());

        assertEquals(studentRepository.count(), 0);
    }

    @Test
    public void updateStudentTest() throws Exception {
        utils.createDefaultStudent();

        final long studentId = studentRepository.findAll().get(0).getId();
        final StudentDto updateDto = new StudentDto(
                "new firstName",
                "new lastName",
                TEST_EMAIL_2,
                "newPwd",
                "+79153335522"
        );

        final var response = utils.perform(
                put("/student/update/" + studentId)
                        .content(asJson(updateDto))
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse();

        final MessageResponse messageResponse = fromJson(response.getContentAsString(), new TypeReference<MessageResponse>() {});

        assertTrue(studentRepository.existsById(studentId));
        assertEquals(messageResponse.getMessage(), new MessageResponse("Student with a name " +
                updateDto.getFirstName() + " " + updateDto.getLastName()) + " was updated");
    }
}
