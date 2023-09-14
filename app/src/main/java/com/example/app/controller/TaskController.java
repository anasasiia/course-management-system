package com.example.app.controller;

import com.example.app.dto.MessageResponse;
import com.example.app.dto.TaskDtoToCreate;
import com.example.app.dto.TaskDtoToUpdate;
import com.example.app.model.Task;
import com.example.app.service.TaskService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/task")
@RequiredArgsConstructor
@Slf4j
public class TaskController {
    private final TaskService taskService;
    private final AmqpTemplate template;

    @GetMapping("/student/{studentId}/{taskId}")
    public ResponseEntity<?> getTaskByIdAndByStudentId(@PathVariable long studentId, @PathVariable long taskId) {
        return ResponseEntity.ok().body(taskService.findTaskByIdAndByStudentId(studentId, taskId));
    }

    @GetMapping("/subject/{subjectId}/{studentId}")
    public ResponseEntity<?> getAllTasksByStudentIdAndSubjectId(@PathVariable long studentId,
                                                                @PathVariable long subjectId) {
        return ResponseEntity.ok().body(taskService.findAllTasksByStudentIdAndSubjectId(studentId, subjectId));
    }

    @GetMapping("/teacher/{teacherId}/{taskId}")
    public ResponseEntity<?> getTaskByIdAndTeacherId(@PathVariable long teacherId, @PathVariable long taskId) {
        return ResponseEntity.ok().body(taskService.findTaskByIdAndByTeacherId(teacherId, taskId));
    }

    @GetMapping("/teacher/{teacherId}/all/{page}")
    public ResponseEntity<?> getAllTasksByTeacherId(@PathVariable long teacherId,
                                                    @PathVariable int page) {
        Pageable pageable = PageRequest.of(page, 5);
        return ResponseEntity.ok().body(taskService.findAllTasksByTeacherId(teacherId, pageable));
    }

    @PostMapping("/create")
    public ResponseEntity<?> createTask(@RequestBody TaskDtoToCreate taskDtoToCreate) {
        try {
            Task task = taskService.create(taskDtoToCreate);

            template.convertAndSend("studentQueue",
                    "New task " + task.getName() + " was created");
            return ResponseEntity.ok().body(task);
        } catch (RuntimeException e) {
            log.error("Task with name " + taskDtoToCreate.getName() + " was not created. Error: " + e.getLocalizedMessage());
            return ResponseEntity.badRequest().body(new MessageResponse("Task with name " + taskDtoToCreate.getName() +
                    " was not created. Error: " + e.getLocalizedMessage()));
        }

    }

    @PutMapping("/addFile/{taskId}")
    public ResponseEntity<?> addFilesToTask(@PathVariable long taskId, @RequestParam("files")MultipartFile[] files) {
        try {
            taskService.addFilesToTask(taskId, files);
            return ResponseEntity.ok().body(new MessageResponse("Files were uploaded"));
        } catch (RuntimeException e) {
            log.error("Files were not uploaded. Error: " + e.getLocalizedMessage());
            return ResponseEntity.badRequest()
                    .body(new MessageResponse("Files were not uploaded. Error: " + e.getLocalizedMessage()));
        }

    }

    @PutMapping("/deleteFile/{taskId}")
    public ResponseEntity<?> deleteFileFromTask(@PathVariable long taskId, @RequestParam("fileUri") String fileUri) {
        try {
            taskService.deleteFileFromTask(taskId, fileUri);
            return ResponseEntity.ok().body(new MessageResponse("File was deleted"));
        } catch (RuntimeException e) {
            log.error("File with uri " + fileUri + " was not deleted. Error: " + e.getLocalizedMessage());
            return ResponseEntity.badRequest().body(new MessageResponse("File with uri " +
                    fileUri + " was not deleted. Error: " + e.getLocalizedMessage()));
        }
    }

    @PutMapping("/update/{taskId}")
    public ResponseEntity<?> updateTask(@PathVariable long taskId, @RequestBody TaskDtoToUpdate taskDtoToUpdate) {
        try {
            taskService.update(taskId, taskDtoToUpdate);
            return ResponseEntity.ok().body(new MessageResponse("Task with name " +
                    taskDtoToUpdate.getName() + " was updated"));
        } catch (RuntimeException e) {
            log.error("Task with name " + taskDtoToUpdate.getName() + " was not updated. Error: " + e.getLocalizedMessage());
            return ResponseEntity.badRequest().body(new MessageResponse("Task with name " + taskDtoToUpdate.getName() +
                    " was not updated. Error: " + e.getLocalizedMessage()));
        }
    }

    @GetMapping("/{taskId}")
    public ResponseEntity<?> getTaskById(@PathVariable long taskId) {
        return ResponseEntity.ok().body(taskService.findTaskById(taskId));
    }

    @DeleteMapping("/{taskId}")
    public ResponseEntity<?> deleteTask(@PathVariable long taskId) {
        taskService.delete(taskId);
        return ResponseEntity.ok().body(new MessageResponse("Task with id " +
                taskId + " was deleted"));
    }
}
