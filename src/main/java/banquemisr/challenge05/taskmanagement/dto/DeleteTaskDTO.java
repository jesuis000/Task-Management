package banquemisr.challenge05.taskmanagement.dto;

public class DeleteTaskDTO {
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public DeleteTaskDTO(String message) {
        this.message = message;
    }

    private String message;
}
