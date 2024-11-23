package banquemisr.challenge05.taskmanagement.models;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity(name = "notifications")
public class NotificationModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserModel user_id;

    @ManyToOne
    @JoinColumn(name = "task_id")
    private TaskModel task_id;

    @Column(nullable = false)
    private String message;

    @Column(name = "is_read")
    private boolean isRead;

    @Column(name = "created_at")
    private LocalDateTime createdAt;
}
