package com.example.app.utils;

import com.example.app.dto.*;
import com.example.app.repository.GroupRepository;
import com.example.app.repository.StudentRepository;
import com.example.app.repository.SubjectRepository;
import com.example.app.repository.TeacherRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import java.util.List;

@Component
public class TestUtils {
    public static final String TEST_EMAIL = "email@email.com";
    public static final String TEST_EMAIL_2 = "email2@email.com";
    private final StudentDto testStudentDto = new StudentDto(
            "David",
            "Black",
            TEST_EMAIL,
            "pwd",
            "+79153332211"
    );

    private final TeacherDto testTeacherDto = new TeacherDto(
            "Marie",
            "Smith",
            TEST_EMAIL_2,
            "pwd",
            "+79059998877",
            "Phd"
    );

    private final GroupDto testGroupDto = new GroupDto(
            "ABC-1",
            List.of(1L)
    );

    private final SubjectDto testSubjectDto = new SubjectDto(
            "Math",
            List.of(1L)
    );

    private final TaskAnswerDto testTaskAnswerDto = new TaskAnswerDto(
            "comment",
            1,
            1
    );

    private final RatingDtoToCreate testRatingDto = new RatingDtoToCreate(
            10,
            "comment",
            1,
            1,
            1
    );

    public StudentDto getTestStudentDto() {
        return testStudentDto;
    }

    public GroupDto getTestGroupDto() {
        return testGroupDto;
    }

    public RatingDtoToCreate getTestRatingDto() {
        return testRatingDto;
    }

    public SubjectDto getTestSubjectDto() {
        return testSubjectDto;
    }

    public TaskAnswerDto getTestTaskAnswerDto() {
        return testTaskAnswerDto;
    }

    public TeacherDto getTestTeacherDto() {
        return testTeacherDto;
    }

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private SubjectRepository subjectRepository;

    public void tearDown() {
        studentRepository.deleteAll();
        teacherRepository.deleteAll();
        groupRepository.deleteAll();
        subjectRepository.deleteAll();
    }

    public ResultActions createDefaultGroup() throws Exception {
        return createGroup(getTestGroupDto());
    }

    public ResultActions createDefaultSubject() throws Exception {
        return createSubject(getTestSubjectDto());
    }

    public ResultActions createDefaultStudent() throws Exception {
        return createStudent(getTestStudentDto());
    }

    public ResultActions createDefaultTeacher() throws Exception {
        return createTeacher(getTestTeacherDto());
    }

    public ResultActions createDefaultRating() throws Exception {
        return createRating(getTestRatingDto());
    }

    public ResultActions createDefaultTaskAnswer() throws Exception {
        return createTaskAnswer(getTestTaskAnswerDto());
    }

    private ResultActions createGroup(GroupDto testGroupDto) throws Exception {
        final var request = post("/group/create")
                .content(asJson(testGroupDto))
                .contentType(MediaType.APPLICATION_JSON);
        return mockMvc.perform(request);
    }

    private ResultActions createSubject(SubjectDto testSubjectDto) throws Exception {
        final var request = post("/subject/create")
                .content(asJson(testSubjectDto))
                .contentType(MediaType.APPLICATION_JSON);
        return mockMvc.perform(request);
    }

    private ResultActions createStudent(StudentDto testStudentDto) throws Exception {
        final var request = post("/student/create")
                .content(asJson(testStudentDto))
                .contentType(MediaType.APPLICATION_JSON);
        return mockMvc.perform(request);
    }

    private ResultActions createTeacher(TeacherDto testTeacherDto) throws Exception {
        final var request = post("/teacher/create")
                .content(asJson(testTeacherDto))
                .contentType(MediaType.APPLICATION_JSON);
        return mockMvc.perform(request);
    }

    private ResultActions createRating(RatingDtoToCreate testRatingDto) throws Exception {
        final var request = post("/rating/create")
                .content(asJson(testRatingDto))
                .contentType(MediaType.APPLICATION_JSON);
        return mockMvc.perform(request);
    }

    private ResultActions createTaskAnswer(TaskAnswerDto testTaskAnswerDto) throws Exception {
        final var request = post("/task-answer/create")
                .content(asJson(testTaskAnswerDto))
                .contentType(MediaType.APPLICATION_JSON);
        return mockMvc.perform(request);
    }

    private static final ObjectMapper MAPPER = new ObjectMapper().findAndRegisterModules();

    public static String asJson(final Object object) throws JsonProcessingException {
        return MAPPER.writeValueAsString(object);
    }

    public static <T> T fromJson(final String json, final TypeReference<T> to) throws JsonProcessingException {
        return MAPPER.readValue(json, to);
    }

    public ResultActions perform(final MockHttpServletRequestBuilder requestBuilder) throws Exception {
        return mockMvc.perform(requestBuilder);
    }
}
