package banquemisr.challenge05.taskmanagement.exception;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    ResponseEntity<ErrorResponse> handleMethodException(MethodArgumentNotValidException e) {
        Map<String, String> errors = new HashMap<>();

        e.getBindingResult().getFieldErrors().forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));


        ErrorResponse response = new ErrorResponse("validation error", 400, errors);

        return ResponseEntity.badRequest().body(response);
    }


    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    ResponseEntity<ErrorResponse> handleSQLIntegrityConstraintViolationException(SQLIntegrityConstraintViolationException e) {
        return ResponseEntity.status(HttpServletResponse.SC_CONFLICT).body(new ErrorResponse("duplication error", HttpServletResponse.SC_CONFLICT, new HashMap<>()));
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    ResponseEntity<ErrorResponse> handleUsernameNotFoundException(UsernameNotFoundException e) {
        return ResponseEntity.status(HttpServletResponse.SC_NOT_FOUND).body(new ErrorResponse("user not found", HttpServletResponse.SC_NOT_FOUND, new HashMap<>()));
    }

    @ExceptionHandler(AccessDeniedException.class)
    ResponseEntity<ErrorResponse> handleAccessDeniedException(AccessDeniedException e) {
        return ResponseEntity.status(HttpServletResponse.SC_FORBIDDEN).body(new ErrorResponse("You can only update/delete your own tasks", HttpServletResponse.SC_FORBIDDEN, new HashMap<>()));
    }

    @ExceptionHandler(EntityNotFoundException.class)
    ResponseEntity<ErrorResponse> handleEntityNotFoundException(EntityNotFoundException e) {
        return ResponseEntity.status(HttpServletResponse.SC_FORBIDDEN).body(new ErrorResponse("Task not found", HttpServletResponse.SC_NOT_FOUND, new HashMap<>()));
    }
}

