package com.example.app.controller;

import com.example.app.dto.MessageResponse;
import com.example.app.dto.RatingDtoToCreate;
import com.example.app.dto.RatingDtoToUpdate;
import com.example.app.model.Rating;
import com.example.app.service.RatingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rating")
@Slf4j
@RequiredArgsConstructor
public class RatingController {
    private final RatingService ratingService;
    private final AmqpTemplate template;

    @GetMapping("/student/{taskId}/{studentId}")
    public ResponseEntity<?> getRatingByTaskIdAndStudentId(@PathVariable long taskId, @PathVariable long studentId) {
        return ResponseEntity.ok().body(ratingService.findByTaskIdAndStudentId(taskId, studentId));
    }

    @GetMapping("/teacher/{taskId}/{studentId}")
    public ResponseEntity<?> getRatingByTaskIdAndTeacherId(@PathVariable long taskId, @PathVariable long teacherId) {
        return ResponseEntity.ok().body(ratingService.findByTaskIdAndTeacherId(taskId, teacherId));
    }

    @GetMapping("/subject/{subjectId}/{studentId}")
    public ResponseEntity<?> getRatingBySubjectIdAndStudentId(@PathVariable long subjectId,
                                                                @PathVariable long studentId) {
        return ResponseEntity.ok().body(ratingService.findBySubjectIdAndStudentId(subjectId, studentId));
    }

    @PostMapping("/create")
    public ResponseEntity<?> createRating(@RequestBody RatingDtoToCreate ratingDtoToCreate) {
        try {
            Rating rating = ratingService.create(ratingDtoToCreate);
            template.convertAndSend("studentQueue",
                    "You task answer was rated. Your mark is " + ratingDtoToCreate.getMark());
            return ResponseEntity.ok().body(rating);
        } catch (RuntimeException e) {
            return ResponseEntity.internalServerError().body(new MessageResponse("Rating for a student with id=" +
                    ratingDtoToCreate.getStudentId() + " was not created. Error: " + e.getLocalizedMessage()));
        }
    }

    @PutMapping("/update/{ratingId}/{teacherId}")
    public ResponseEntity<?> updateRating(@PathVariable long ratingId, @PathVariable long teacherId,
                                          @RequestBody RatingDtoToUpdate ratingDtoToUpdate) {
        try {
            ratingService.update(ratingId, teacherId, ratingDtoToUpdate);
            return ResponseEntity.ok().body(new MessageResponse("Rating with id=" + ratingId +
                    "was updated by teacher with id=" + teacherId));
        } catch (RuntimeException e) {
            return ResponseEntity.internalServerError().body(new MessageResponse("Rating with id=" + ratingId + " was not updated. Error: " +
                    e.getLocalizedMessage()));
        }
    }
}
