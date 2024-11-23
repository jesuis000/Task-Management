package banquemisr.challenge05.taskmanagement.dto;

import banquemisr.challenge05.taskmanagement.models.TaskModel;
import banquemisr.challenge05.taskmanagement.models.TaskPriority;
import banquemisr.challenge05.taskmanagement.models.TaskStatus;
import banquemisr.challenge05.taskmanagement.models.UserModel;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

public class CreateTaskDTO {
    @NotBlank(message = "Title is required")
    @Size(max = 100, message = "Title must be less than 100 characters")
    private String title;

    @Size(max = 500, message = "Description must be less than 500 characters")
    private String description;


    public @NotBlank(message = "Title is required") @Size(max = 100, message = "Title must be less than 100 characters") String getTitle() {
        return title;
    }

    public void setTitle(@NotBlank(message = "Title is required") @Size(max = 100, message = "Title must be less than 100 characters") String title) {
        this.title = title;
    }

    public @Size(max = 500, message = "Description must be less than 500 characters") String getDescription() {
        return description;
    }

    public void setDescription(@Size(max = 500, message = "Description must be less than 500 characters") String description) {
        this.description = description;
    }

    public @NotNull(message = "Status is required") TaskStatus getStatus() {
        return status;
    }

    public void setStatus(@NotNull(message = "Status is required") TaskStatus status) {
        this.status = status;
    }

    public @NotNull(message = "Priority is required") TaskPriority getPriority() {
        return priority;
    }

    public void setPriority(@NotNull(message = "Priority is required") TaskPriority priority) {
        this.priority = priority;
    }

    public @NotNull @Future(message = "Due date must be in the future") LocalDateTime getDueDate() {
        return dueDate;
    }

    public void setDueDate(@NotNull @Future(message = "Due date must be in the future") LocalDateTime dueDate) {
        this.dueDate = dueDate;
    }

    @NotNull(message = "Status is required")
    private TaskStatus status;

    @NotNull(message = "Priority is required")
    private TaskPriority priority;

    @NotNull
    @Future(message = "Due date must be in the future")
    private LocalDateTime dueDate;

    public UserModel getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(UserModel createdBy) {
        this.createdBy = createdBy;
    }

    public UserModel getAssignee() {
        return assignee;
    }

    public void setAssignee(UserModel assignee) {
        this.assignee = assignee;
    }

    private UserModel createdBy;
    private UserModel assignee;

    public static TaskModel toTaskModel(CreateTaskDTO createTaskDTO) {
        TaskModel taskModel = new TaskModel();

        taskModel.setPriority(createTaskDTO.getPriority());
        taskModel.setDueDate(createTaskDTO.getDueDate());
        taskModel.setTitle(createTaskDTO.getTitle());
        taskModel.setDescription(createTaskDTO.getDescription());
        taskModel.setStatus(createTaskDTO.getStatus());
        taskModel.setAssignee(createTaskDTO.getAssignee());
        taskModel.setCreatedBy(createTaskDTO.getCreatedBy());

        return taskModel;
    }
}