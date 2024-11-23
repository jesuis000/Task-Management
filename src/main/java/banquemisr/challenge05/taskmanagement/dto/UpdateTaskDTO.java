package banquemisr.challenge05.taskmanagement.dto;


import banquemisr.challenge05.taskmanagement.models.TaskModel;
import banquemisr.challenge05.taskmanagement.models.TaskPriority;
import banquemisr.challenge05.taskmanagement.models.TaskStatus;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

public class UpdateTaskDTO {
    @Size(max = 100, message = "Title must be less than 100 characters")
    private String title;

    @Size(max = 500, message = "Description must be less than 500 characters")
    private String description;

    private TaskStatus status;

    private TaskPriority priority;

    public LocalDateTime getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(LocalDateTime updated_at) {
        this.updated_at = updated_at;
    }

    private LocalDateTime updated_at;

    @Future(message = "Due date must be in the future")
    private LocalDateTime dueDate;

    public @Size(max = 100, message = "Title must be less than 100 characters") String getTitle() {
        return title;
    }

    public void setTitle(@Size(max = 100, message = "Title must be less than 100 characters") String title) {
        this.title = title;
    }

    public @Size(max = 500, message = "Description must be less than 500 characters") String getDescription() {
        return description;
    }

    public void setDescription(@Size(max = 500, message = "Description must be less than 500 characters") String description) {
        this.description = description;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public TaskPriority getPriority() {
        return priority;
    }

    public void setPriority(TaskPriority priority) {
        this.priority = priority;
    }

    public @Future(message = "Due date must be in the future") LocalDateTime getDueDate() {
        return dueDate;
    }

    public void setDueDate(@Future(message = "Due date must be in the future") LocalDateTime dueDate) {
        this.dueDate = dueDate;
    }

    public TaskModel updateTaskModel(TaskModel existingTask) {
        if (title != null) existingTask.setTitle(title);
        if (description != null) existingTask.setDescription(description);
        if (status != null) existingTask.setStatus(status);
        if (priority != null) existingTask.setPriority(priority);
        if (dueDate != null) existingTask.setDueDate(dueDate);

        return existingTask;
    }

}