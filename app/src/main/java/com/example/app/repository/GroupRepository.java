package com.example.app.repository;

import com.example.app.model.Group;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GroupRepository extends JpaRepository<Group, Long> {
    Optional<Group> findById(long id);

    boolean existsById(long id);

    Optional<Group> findByName(String name);

    boolean existsByName(String name);

    @Query(value = "SELECT g FROM Group g JOIN g.subjects s JOIN s.teachers t WHERE t.id = ?1 AND g.id = ?2")
    Group findByTeacherIdAndGroupId(long teacherId, long id);
}
