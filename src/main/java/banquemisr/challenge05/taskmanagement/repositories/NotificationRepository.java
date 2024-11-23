package banquemisr.challenge05.taskmanagement.repositories;

import banquemisr.challenge05.taskmanagement.models.NotificationModel;
import banquemisr.challenge05.taskmanagement.models.TaskModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface NotificationRepository extends JpaRepository<NotificationModel, Long> {

    @Modifying
    @Query("DELETE FROM notifications n WHERE n.task_id = :taskModel")
    void deleteAllByTask(@Param("taskModel") TaskModel taskModel);
}
