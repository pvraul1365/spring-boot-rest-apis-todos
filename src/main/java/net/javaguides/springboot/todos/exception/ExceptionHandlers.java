package net.javaguides.springboot.todos.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;

/**
 * ExceptionHandlers
 * <p>
 * Created by IntelliJ, Spring Framework Guru.
 *
 * @author architecture - pvraul
 * @version 02/07/2026 - 19:37
 * @since 1.17
 */
@ControllerAdvice
public class ExceptionHandlers {

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ExceptionResponses> handleException(ResponseStatusException e) {
        return this.buildResponseEntity(e, HttpStatus.valueOf(e.getStatusCode().value()));
    }
    
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponses> handleException(Exception e) {
        return this.buildResponseEntity(e, HttpStatus.BAD_REQUEST);
    }

    private ResponseEntity<ExceptionResponses> buildResponseEntity(final Exception exception, final HttpStatus  status) {
        ExceptionResponses exceptionResponses = ExceptionResponses.builder()
                .status(status.value())
                .message(exception.getMessage())
                .timeStamp(System.currentTimeMillis())
                .build();
        return new ResponseEntity<>(exceptionResponses, status);
    }

}
