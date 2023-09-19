package com.example.app.controller;

import com.example.app.dto.MessageResponse;
import com.example.app.dto.TaskAnswerDto;
import com.example.app.model.TaskAnswer;
import com.example.app.service.TaskAnswerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/task-answer")
@RequiredArgsConstructor
@Slf4j
public class TaskAnswerController {
    private final TaskAnswerService taskAnswerService;
    private final AmqpTemplate template;
    @GetMapping("/for-student/{studentId}/{taskId}")
    public ResponseEntity<?> getAnswerByStudentIdAndByTaskId(@PathVariable long studentId, @PathVariable long taskId) {
        return ResponseEntity.ok().body(taskAnswerService.findByStudentIdAndByTaskId(studentId, taskId));
    }

    @GetMapping("/for-teacher/{teacherId}/{taskId}")
    public ResponseEntity<?> getAnswerByTeacherIdAndByTaskId(@PathVariable long teacherId, @PathVariable long taskId) {
        return ResponseEntity.ok().body(taskAnswerService.findByTeacherIdAndTaskId(teacherId, taskId));
    }

    @PutMapping("/addFile/{answerId}")
    public ResponseEntity<?> addFileToAnswer(@PathVariable long answerId, @RequestParam MultipartFile[] files) {
        try {
            taskAnswerService.addFileToAnswer(answerId, files);
            return ResponseEntity.ok().body(new MessageResponse("Files were added to answer with id " + answerId));
        } catch (RuntimeException e) {
            return ResponseEntity.internalServerError().body(new MessageResponse("Files can not be added to answer with id " +
                    answerId + ". Error: " + e.getLocalizedMessage()));
        }
    }

    @PutMapping("/deleteFile/{answerId}")
    public ResponseEntity<?> deleteFileFromAnswer(@PathVariable long answerId, @RequestParam("fileUri") String fileUri) {
        try {
            taskAnswerService.deleteFileFromAnswer(answerId, fileUri);
            return ResponseEntity.ok().body(new MessageResponse("Files were deleted from answer with id " + answerId));
        } catch (RuntimeException e) {
            return ResponseEntity.internalServerError()
                    .body(new MessageResponse("File can not be deleted from answer with " + answerId +
                            ". Error: " + e.getLocalizedMessage()));
        }
    }

    @PostMapping("/create")
    public ResponseEntity<?> createAnswer(@RequestBody TaskAnswerDto answerDto) {
        try {
            TaskAnswer answer = taskAnswerService.create(answerDto);
            template.convertAndSend("teacherQueue",
                    answer.getStudent().getFirstName() + " " + answer.getStudent().getLastName() +
                            "from the group " + answer.getStudent().getGroup() + " sent answer for the task");
            return ResponseEntity.ok().body(answer);
        } catch (RuntimeException e) {
            return ResponseEntity.internalServerError().body(new MessageResponse("Answer " + answerDto.getComment() +
                    " was not sent. Error: " + e.getLocalizedMessage()));
        }
    }

    @PutMapping("/update/{answerId}")
    public ResponseEntity<?> updateAnswer(@PathVariable long answerId, @RequestBody TaskAnswerDto answerDto) {
        try {
            taskAnswerService.update(answerId, answerDto);
            return ResponseEntity.ok().body(new MessageResponse("Answer was updated"));
        } catch (RuntimeException e) {
            return ResponseEntity.internalServerError().body(new MessageResponse("Answer can not be updated. Error: " +
                    e.getLocalizedMessage()));
        }
    }

    @GetMapping("/{answerId}")
    public ResponseEntity<?> getAnswerById(@PathVariable long answerId) {
        return ResponseEntity.ok().body(taskAnswerService.findById(answerId));
    }

    @DeleteMapping("/{answerId}")
    public ResponseEntity<?> deleteAnswer(@PathVariable long answerId) {
        try {
            taskAnswerService.delete(answerId);
            return ResponseEntity.ok().body(new MessageResponse("Answer with id " + answerId + " was deleted"));
        } catch (RuntimeException e) {
            return ResponseEntity.internalServerError().body(new MessageResponse("Answer with id=" + answerId +
                    " was not deleted. Error: " + e.getLocalizedMessage()));
        }
    }
}
