package banquemisr.challenge05.taskmanagement.controllers;

import banquemisr.challenge05.taskmanagement.dto.CreateTaskDTO;
import banquemisr.challenge05.taskmanagement.dto.DeleteTaskDTO;
import banquemisr.challenge05.taskmanagement.dto.GetTaskDTO;
import banquemisr.challenge05.taskmanagement.dto.UpdateTaskDTO;
import banquemisr.challenge05.taskmanagement.models.TaskModel;
import banquemisr.challenge05.taskmanagement.models.UserModel;
import banquemisr.challenge05.taskmanagement.repositories.TaskRepository;
import banquemisr.challenge05.taskmanagement.repositories.UserRepository;
import banquemisr.challenge05.taskmanagement.services.TaskService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {
    private static final Logger logger = LoggerFactory.getLogger(TaskController.class);

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final TaskService taskService;

    TaskController(TaskRepository taskRepository, UserRepository userRepository, TaskService taskService) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
        this.taskService = taskService;
    }

    @GetMapping("/")
    List<GetTaskDTO> getTasks(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            Authentication authentication
    ) {
        // Log the incoming request details
        logger.info("Received request to fetch tasks - Page: {}, Size: {}, User: {}",
                page, size, authentication.getName());

        Pageable pageable = PageRequest.of(page, size);

        // current user name from spring security (Authentication)
        UserModel userModel = userRepository.findByUserName(authentication.getName());


        // current user assigned tasks , mapped to taskDTO
        return taskRepository
                .findByassignee(userModel, pageable)
                .stream()
                .map(GetTaskDTO::fromTaskModel)
                .toList();

    }

    @PostMapping("/create")
    GetTaskDTO createTask(@Valid @RequestBody CreateTaskDTO createTaskDTO, Authentication authentication) {

        UserModel userModel = userRepository.findByUserName(authentication.getName());

        createTaskDTO.setCreatedBy(userModel);

        createTaskDTO.setAssignee(userModel);

        return taskService.insertTask(createTaskDTO);
    }

    @PutMapping("/{taskId}")
    public ResponseEntity<UpdateTaskDTO> updateTask(@PathVariable Long taskId, @Valid @RequestBody UpdateTaskDTO updateTaskDTO, Authentication authentication) {

        UserModel userModel = userRepository.findByUserName(authentication.getName());


        UpdateTaskDTO updatedTask = taskService.updateTask(taskId, updateTaskDTO, userModel);

        return ResponseEntity.ok(updatedTask);
    }

    @DeleteMapping("/{taskId}")
    public ResponseEntity<?> deleteTask(@PathVariable Long taskId, Authentication authentication) {
        UserModel userModel = userRepository.findByUserName(authentication.getName());

        taskService.deleteTask(taskId, userModel);

        return ResponseEntity.ok().body(new DeleteTaskDTO("Task Deleted Successfully"));
    }
}