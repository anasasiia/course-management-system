package com.example.app.service.impl;

import com.example.app.dto.InstructorDto;
import com.example.app.model.Instructor;
import com.example.app.repository.InstructorRepository;
import com.example.app.service.InstructorService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@AllArgsConstructor
public class InstructorServiceImpl implements InstructorService {
    private InstructorRepository instructorRepository;
    @Override
    public Instructor createInstructor(final InstructorDto instructorDto) {
        final Instructor instructor = new Instructor();
        instructor.setUserName(instructorDto.getUserName());
        instructor.setFirstName(instructorDto.getFirstName());
        instructor.setLastName(instructorDto.getLastName());
        instructor.setPassword(instructorDto.getPassword());
        return instructorRepository.save(instructor);
    }

    @Override
    public Instructor updateInstructor(final long id, final InstructorDto instructorDto) {
        final Instructor instructor = instructorRepository.getById(id);
        instructor.setUserName(instructorDto.getUserName());
        instructor.setFirstName(instructorDto.getFirstName());
        instructor.setLastName(instructorDto.getLastName());
        instructor.setPassword(instructorDto.getPassword());
        return instructorRepository.save(instructor);
    }
}
