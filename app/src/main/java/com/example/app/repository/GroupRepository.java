package com.example.app.repository;

import com.example.app.model.Group;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GroupRepository extends JpaRepository<Group, Long> {
    Optional<Group> findById(long id);
    Page<Group> findAll(Pageable pageable);

    boolean existById(long id);

    Optional<Group> findByName(String name);

    boolean existByName(String name);

    @Query(value = "SELECT g" +
            "FROM GROUP g" +
            "JOIN group.subjects s" +
            "JOIN subject.teachers t" +
            "WHERE t.id = ?1 AND g.id = ?2")
    Group findByTeacherIdAndGroupId(long teacherId, long id);
}
