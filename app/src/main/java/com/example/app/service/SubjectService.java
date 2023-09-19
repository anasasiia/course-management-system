package com.example.app.service;

import com.example.app.dto.SubjectDto;
import com.example.app.model.Subject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SubjectService {
    Page<Subject> findSubjectByNameAndByTeacherId(long teacherId, String subjectName, Pageable pageable);

    Page<Subject> findAllSubjectsByTeacherId(long teacherId, Pageable pageable);

    Page<Subject> findAllSubjectsByGroupId(long groupId, Pageable pageable);
    Subject findSubjectById(long id);
    void create(SubjectDto subjectDto);
    void delete(long id);
    void addGroupToSubject(long subjectId, long groupId);
    void deleteGroupToSubject(long subjectId, long groupId);
    void addTeacherToSubject(long subjectId, long teacherId);
    void deleteTeacherToSubject(long subjectId, long teacherId);
}
