package com.example.app.repository;

import com.example.app.model.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    Optional<Student> findStudentById(long id);
    Page<Student> findAll(Pageable pageable);

}
