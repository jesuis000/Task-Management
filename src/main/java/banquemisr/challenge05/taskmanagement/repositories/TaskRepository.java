package banquemisr.challenge05.taskmanagement.repositories;

import banquemisr.challenge05.taskmanagement.annotation.LogExecutionTime;
import banquemisr.challenge05.taskmanagement.models.TaskModel;
import banquemisr.challenge05.taskmanagement.models.UserModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<TaskModel, Long> {
    @LogExecutionTime
    Page<TaskModel> findByassignee(UserModel assignee, Pageable pageable);
}
