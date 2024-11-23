# Task Management System

A Spring Boot-based microservice for managing tasks with JWT authentication, role-based authorization

## Prerequisites

- Java 17 or higher
- Docker and Docker Compose
- Mysql (provided via Docker)

## Database Schema

The database schema is documented in the `erduml` file in the project root. This diagram shows the relationships
between:

- Users
- Tasks
- Task History
- Notifications

## Quick Start

1. Clone the repository:

```bash
git clone https://github.com/jesuis000/Task-Management.git
cd task-management
```

2. Build the application:

```bash
mvn clean package
```

3. Start the application using Docker Compose:

```bash
docker-compose up
```

The application will be available at `http://localhost:8080`

## API Documentation

Once the application is running, you can access postman documentation at:
`https://documenter.getpostman.com/view/15415441/2sAYBUDC3f`

## Default Credentials

The system comes with pre-configured users for testing:

- Admin User:
    - userName: admin
    - passWord: $2a$10$ExampleHashedPassword123

- Regular User:
    - Username: john_doe
    - Password: $2a$10$ExampleHashedPassword456

NOTE : if you faced any login issues using these previous users , please use endpoint /users/register to create new
fresh one

## Stopping the Application

To stop the application and remove containers:

```bash
docker-compose down
```
