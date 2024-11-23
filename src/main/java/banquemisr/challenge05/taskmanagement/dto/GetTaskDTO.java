package banquemisr.challenge05.taskmanagement.dto;

import banquemisr.challenge05.taskmanagement.models.TaskModel;
import banquemisr.challenge05.taskmanagement.models.TaskPriority;
import banquemisr.challenge05.taskmanagement.models.TaskStatus;

import java.time.LocalDateTime;

public class GetTaskDTO {
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
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

    public LocalDateTime getDueData() {
        return dueData;
    }

    public void setDueData(LocalDateTime dueData) {
        this.dueData = dueData;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(LocalDateTime updated_at) {
        this.updated_at = updated_at;
    }

    public GetTaskDTO() {

    }

    public GetTaskDTO(Long id, String title, String description, TaskStatus status, TaskPriority priority, LocalDateTime dueData, LocalDateTime updated_at) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.status = status;
        this.priority = priority;
        this.dueData = dueData;
        this.updated_at = updated_at;
    }

    private String title;
    private String description;
    private TaskStatus status;
    private TaskPriority priority;
    private LocalDateTime dueData;
    private final LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime updated_at;

    public static GetTaskDTO fromTaskModel(TaskModel taskModel) {
        GetTaskDTO getTaskDTO = new GetTaskDTO();

        getTaskDTO.setDescription(taskModel.getDescription());
        getTaskDTO.setId(taskModel.getId());
        getTaskDTO.setPriority(taskModel.getPriority());
        getTaskDTO.setTitle(taskModel.getTitle());
        getTaskDTO.setStatus(taskModel.getStatus());
        getTaskDTO.setDueData(taskModel.getDueDate());
        getTaskDTO.setUpdated_at(taskModel.getUpdated_at());


        return getTaskDTO;
    }
}
