package com.example.app.controller;

import com.example.app.dto.GroupDto;
import com.example.app.dto.MessageResponse;
import com.example.app.dto.StudentDto;
import com.example.app.model.Group;
import com.example.app.repository.GroupRepository;
import com.example.app.repository.StudentRepository;
import com.example.app.utils.TestUtils;
import com.fasterxml.jackson.core.type.TypeReference;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static com.example.app.utils.TestUtils.asJson;
import static com.example.app.utils.TestUtils.fromJson;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Collections;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = RANDOM_PORT)
public class GroupControllerTest {
    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private TestUtils utils;

    @AfterEach
    public final void clear() {
        utils.tearDown();
    }

    @Test
    public void createGroupTest() throws Exception {
        final GroupDto groupDto = new GroupDto(
                "WEB-1",
                new ArrayList<>()
        );

        final var request = post("/group/create")
                .content(asJson(groupDto))
                .contentType(APPLICATION_JSON);

        utils.perform(request).andExpect(status().isOk());
        assertEquals(1, groupRepository.count());
    }

    @Test
    public void deleteGroupTest() throws Exception {
        utils.createDefaultStudent();

        final long studentId = studentRepository.findAll().get(0).getId();

        GroupDto groupDto = new GroupDto(
                "WEB-1",
                new ArrayList<>(Collections.singletonList(studentId))
        );

        utils.perform(post("/group/create")
                .content(asJson(groupDto))
                .contentType(APPLICATION_JSON));

        final long groupId = groupRepository.findAll().get(0).getId();

        utils.perform(delete("/group/" + groupId))
                .andExpect(status().isOk());

        assertEquals(0, groupRepository.count());
    }

    @Test
    public void getGroupByIdTest() throws Exception {
        utils.createDefaultStudent();

        final long studentId = studentRepository.findAll().get(0).getId();

        GroupDto groupDto = new GroupDto(
                "WEB-1",
                new ArrayList<>(Collections.singletonList(studentId))
        );

        utils.perform(post("/group/create")
                .content(asJson(groupDto))
                .contentType(APPLICATION_JSON));

        final Group expectedGroup = groupRepository.findAll().get(0);

        final var response = utils.perform(
                        get("/group/" + expectedGroup.getId()))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse();

        Group group = fromJson(response.getContentAsString(), new TypeReference<Group>() {
        });

        assertEquals(group.getName(), expectedGroup.getName());
    }

    @Test
    public void addStudentToGroupTest() throws Exception {
        GroupDto groupDto = new GroupDto(
                "WEB-1",
                new ArrayList<>()
        );

        utils.perform(post("/group/create")
                .content(asJson(groupDto))
                .contentType(APPLICATION_JSON));

        final StudentDto studentDto = utils.getTestStudentDto();

        utils.perform(post("/student/create")
                .content(asJson(studentDto))
                .contentType(APPLICATION_JSON));

        final long studentId = studentRepository.findAll().get(0).getId();
        final long groupId = groupRepository.findAll().get(0).getId();

        final var response = utils.perform(
                put("/group/update/" + groupId + "/" + studentId))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse();

        final MessageResponse messageResponse = fromJson(response.getContentAsString(), new TypeReference<MessageResponse>(){});

        assertEquals(messageResponse.getMessage(), "Student with id " + studentId
                + " was added to the group with id " + groupId);
    }

    @Test
    public void deleteStudentToGroupTest() throws Exception {
        GroupDto groupDto = new GroupDto(
                "WEB-1",
                new ArrayList<>()
        );

        utils.perform(post("/group/create")
                .content(asJson(groupDto))
                .contentType(APPLICATION_JSON));

        final StudentDto studentDto = utils.getTestStudentDto();

        utils.perform(post("/student/create")
                .content(asJson(studentDto))
                .contentType(APPLICATION_JSON));

        final long studentId = studentRepository.findAll().get(0).getId();
        final long groupId = groupRepository.findAll().get(0).getId();

        final var response = utils.perform(
                        put("/group/delete/" + groupId + "/" + studentId))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse();

        final MessageResponse messageResponse = fromJson(response.getContentAsString(), new TypeReference<MessageResponse>(){});

        assertEquals(messageResponse.getMessage(), "Student with id " + studentId
                + " was deleted from the group with id " + groupId);
    }

//    @Test
//    public void getStudentsByGroupIdTest() throws Exception {
//        GroupDto groupDto = new GroupDto(
//                "WEB-1",
//                new ArrayList<>()
//        );
//
//        utils.perform(post("/group/create")
//                .content(asJson(groupDto))
//                .contentType(APPLICATION_JSON));
//
//        final StudentDto studentDto = utils.getTestStudentDto();
//
//        utils.perform(post("/student/create")
//                .content(asJson(studentDto))
//                .contentType(APPLICATION_JSON));
//
//        final long studentId = studentRepository.findAll().get(0).getId();
//        final long groupId = groupRepository.findAll().get(0).getId();
//
//        utils.perform(
//                put("/group/update/" + groupId + "/" + studentId))
//                .andExpect(status().isOk())
//                .andReturn()
//                .getResponse();
//
//        final var response = utils.perform(
//                get("/group/" + groupId + "/students"))
//                .andExpect(status().isOk())
//                .andReturn()
//                .getResponse();
//
//        List<Student> students = fromJson(response.getContentAsString(), new TypeReference<List<Student>>() {
//        });
//
//        assertEquals(1, students.size());
//    }
}
