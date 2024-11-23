package banquemisr.challenge05.taskmanagement.services;

import banquemisr.challenge05.taskmanagement.dto.CreateTaskDTO;
import banquemisr.challenge05.taskmanagement.dto.GetTaskDTO;
import banquemisr.challenge05.taskmanagement.dto.UpdateTaskDTO;
import banquemisr.challenge05.taskmanagement.models.TaskModel;
import banquemisr.challenge05.taskmanagement.models.UserModel;
import banquemisr.challenge05.taskmanagement.repositories.NotificationRepository;
import banquemisr.challenge05.taskmanagement.repositories.TaskRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public class TaskService {
    private final TaskRepository taskRepository;
    private final NotificationRepository notificationRepository;

    TaskService(TaskRepository taskRepository, NotificationRepository notificationRepository) {

        this.taskRepository = taskRepository;
        this.notificationRepository = notificationRepository;
    }

    public GetTaskDTO insertTask(CreateTaskDTO createTaskDTO) {
        TaskModel taskModel = CreateTaskDTO.toTaskModel(createTaskDTO);

        taskRepository.save(taskModel);

        return GetTaskDTO.fromTaskModel(taskModel);
    }

    public UpdateTaskDTO updateTask(Long taskId, UpdateTaskDTO updateTaskDTO, UserModel userModel) {

        TaskModel existingTask = taskRepository.findById(taskId)
                .orElseThrow(() -> new EntityNotFoundException("Task not found"));


        if (!existingTask.getCreatedBy().getId().equals(userModel.getId())) {

            throw new AccessDeniedException("You can only update/delete your own tasks");
        }

        TaskModel updatedTask = updateTaskDTO.updateTaskModel(existingTask);

        taskRepository.save(updatedTask);

        updateTaskDTO.setUpdated_at(LocalDateTime.now());

        return updateTaskDTO;
    }

    @Transactional
    public void deleteTask(Long taskId, UserModel currentUser) {
        TaskModel task = taskRepository.findById(taskId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Task not found"
                ));


        if (!task.getCreatedBy().getId().equals(currentUser.getId())) {
            throw new AccessDeniedException("You can only update/delete your own tasks");
        }

        notificationRepository.deleteAllByTask(task);

        taskRepository.delete(task);
    }
}