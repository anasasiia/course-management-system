package com.example.app.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "task_ answers")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TaskAnswer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "taskAnswer_id")
    private long id;

    @Column(name = "comment")
    private String comment;

    @Column(name = "date")
    private Date date = new Date();

    @ElementCollection
    @CollectionTable(name = "tasks_answers_files", joinColumns = @JoinColumn(name = "task_ answer_id"))
    @Column(name = "file_uri")
    private Set<String> answersUri;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "student_id")
    private Student student;

    @JsonManagedReference
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "task_id")
    private Task task;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || o.getClass() != getClass()) {
            return false;
        }

        TaskAnswer answer = (TaskAnswer) o;

        if (answer.id != id) {
            return false;
        }

        if (!answer.comment.equals(comment)) {
            return false;
        }

        if (!answer.date.equals(date)) {
            return false;
        }

        if (!answer.task.equals(task)) {
            return false;
        }

        if (!answer.student.equals(student)) {
            return false;
        }

        if (!answer.answersUri.equals(answersUri)) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int total = (int) id;
        total = 31 * (int) id + total;
        total = 31 * total + (comment != null ? comment.hashCode() : 0);
        total = 31 * total + (date != null ? date.hashCode() : 0);
        total = 31 * total + (task != null ? task.hashCode() : 0);
        total = 31 * total + (student != null ? student.hashCode() : 0);
        total = 31 * total + (answersUri != null ? answersUri.hashCode() : 0);
        return total;
    }
}
