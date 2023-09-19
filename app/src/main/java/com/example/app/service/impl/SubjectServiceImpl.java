package com.example.app.service.impl;

import com.example.app.dto.SubjectDto;
import com.example.app.model.Subject;
import com.example.app.repository.SubjectRepository;
import com.example.app.service.GroupService;
import com.example.app.service.SubjectService;
import com.example.app.service.TeacherService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class SubjectServiceImpl implements SubjectService {

    private final SubjectRepository subjectRepository;

    private final TeacherService teacherService;

    private final GroupService groupService;

    @Override
    public Page<Subject> findSubjectByNameAndByTeacherId(long teacherId, String subjectName, Pageable pageable) {
        return subjectRepository.findSubjectByNameAndByTeacherId(teacherId, subjectName, pageable);
    }

    @Override
    public Page<Subject> findAllSubjectsByTeacherId(long teacherId, Pageable pageable) {
        return subjectRepository.findAllSubjectsByTeacherId(teacherId, pageable);
    }

    @Override
    public Page<Subject> findAllSubjectsByGroupId(long groupId, Pageable pageable) {
        return subjectRepository.findAllSubjectsByGroupId(groupId, pageable);
    }

    @Override
    public Subject findSubjectById(long id) {
        return subjectRepository.findById(id).orElseThrow(() -> new RuntimeException("Subject with id " +
                id + " is not found"));
    }

    @Override
    public void create(SubjectDto subjectDto) {
        Subject subject = new Subject();
        subject.setName(subjectDto.getName());
        subjectDto.getTeachersId().forEach(id -> subject.getTeachers().add(teacherService.findTeacherById(id)));
        subjectRepository.save(subject);
    }

    @Override
    public void delete(long id) {
        if (subjectRepository.existsById(id)) {
            subjectRepository.deleteById(id);
        } else {
            throw new RuntimeException("Subject with id " + id + " does not exist");
        }
    }

    @Override
    public void addGroupToSubject(long subjectId, long groupId) {
        Subject subject = findSubjectById(subjectId);
        subject.getGroups().add(groupService.findGroupById(groupId));
        subjectRepository.save(subject);
    }

    @Override
    public void deleteGroupToSubject(long subjectId, long groupId) {
        Subject subject = findSubjectById(subjectId);
        subject.getGroups().remove(groupService.findGroupById(groupId));
        subjectRepository.save(subject);
    }

    @Override
    public void addTeacherToSubject(long subjectId, long teacherId) {
        Subject subject = findSubjectById(subjectId);
        subject.getTeachers().add(teacherService.findTeacherById(teacherId));
        subjectRepository.save(subject);
    }

    @Override
    public void deleteTeacherToSubject(long subjectId, long teacherId) {
        Subject subject = findSubjectById(subjectId);
        subject.getTeachers().remove(teacherService.findTeacherById(teacherId));
        subjectRepository.save(subject);
    }
}
