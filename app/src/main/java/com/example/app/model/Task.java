package com.example.app.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "task")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "task_id")
    private long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "start_time")
    private Date startLine;

    @Column(name = "dead_time")
    private Date deadLine;

    @ElementCollection
    @CollectionTable(name = "tasks_files", joinColumns = @JoinColumn(name = "task_id"))
    @Column(name = "file_uri")
    private Set<String> fileUri = new HashSet<>();

    @JsonManagedReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "teacher_id")
    private Teacher teacher;

    @JsonManagedReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id")
    private Group group;

    @JsonBackReference
    @OneToMany(mappedBy = "task", fetch = FetchType.LAZY)
    private Set<TaskAnswer> taskAnswerSet = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || o.getClass() != getClass()) {
            return false;
        }

        Task task = (Task) o;

        if (task.id != id) {
            return false;
        }

        if (!task.name.equals(name)) {
            return false;
        }

        if (!task.description.equals(description)) {
            return false;
        }

        if (!task.startLine.equals(startLine)) {
            return false;
        }

        if (!task.deadLine.equals(deadLine)) {
            return false;
        }

        if (!task.group.equals(group)) {
            return false;
        }

        if (!task.teacher.equals(teacher)) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int total = (int) id;
        total = 31 * (int) id + total;
        total = 31 * total + (name != null ? name.hashCode() : 0);
        total = 31 * total + (description != null ? description.hashCode() : 0);
        total = 31 * total + (startLine != null ? startLine.hashCode() : 0);
        total = 31 * total + (deadLine != null ? deadLine.hashCode() : 0);
        total = 31 * total + (group != null ? group.hashCode() : 0);
        total = 31 * total + (teacher != null ? teacher.hashCode() : 0);
        return total;
    }
}
