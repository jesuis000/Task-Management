package banquemisr.challenge05.taskmanagement.dto;

import banquemisr.challenge05.taskmanagement.models.UserModel;
import banquemisr.challenge05.taskmanagement.models.UserRole;

import java.time.LocalDateTime;

public class RegisterDTO {
    private long id;
    private String userName;
    private UserRole userRole;
    private String email;
    private LocalDateTime createdAt;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public UserRole getUserRole() {
        return userRole;
    }

    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public static RegisterDTO fromUserModel(UserModel userModel) {
        RegisterDTO registerDTO = new RegisterDTO();
        registerDTO.setCreatedAt(userModel.getCreatedAt());
        registerDTO.setEmail(userModel.getEmail());
        registerDTO.setId(userModel.getId());
        registerDTO.setUserRole(userModel.getUserRole());
        return registerDTO;
    }

}