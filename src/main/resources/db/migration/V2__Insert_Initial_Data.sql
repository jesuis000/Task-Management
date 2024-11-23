
-- Insert Users
INSERT IGNORE INTO users (username, email, password, role) VALUES
('admin', 'admin@example.com', '$2a$10$ExampleHashedPassword123', 'ADMIN'),
('john_doe', 'john@example.com', '$2a$10$ExampleHashedPassword456', 'USER'),
('jane_smith', 'jane@example.com', '$2a$10$ExampleHashedPassword789', 'USER');


-- Insert Tasks
INSERT IGNORE INTO tasks (title, description, status, priority, due_date, assignee,created_by) VALUES
('Complete Backend Project', 'Finish Spring Boot task management system', 'IN_PROGRESS', 'HIGH', '2024-02-28 23:59:59', 1,2),
('Review Documentation', 'Check and update project documentation', 'TODO', 'MEDIUM', '2024-02-15 23:59:59', 2,3),
('Implement JWT Authentication', 'Add security features to the application', 'TODO', 'CRITICAL', '2024-02-20 23:59:59',3,1),
('Prepare Weekly Report', 'Compile team progress report', 'DONE', 'LOW', '2024-02-10 23:59:59', 1,2);


-- Insert Task History
INSERT IGNORE INTO task_history (task_id, previous_status, new_status, modified_by) VALUES
(1, 'TODO', 'IN_PROGRESS', 1),
(3, 'TODO', 'IN_PROGRESS', 2),
(4, 'IN_PROGRESS', 'DONE', 3);


-- Insert Notifications
INSERT IGNORE INTO notifications (user_id, task_id, message, is_read) VALUES
(1, 1, 'Task "Complete Backend Project" is due soon', FALSE),
(2, 2, 'New task assigned: Review Documentation', FALSE),
(1, 3, 'High priority task "Implement JWT Authentication" created', FALSE);




