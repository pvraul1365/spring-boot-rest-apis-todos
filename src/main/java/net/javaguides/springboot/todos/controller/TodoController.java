package net.javaguides.springboot.todos.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.javaguides.springboot.todos.request.TodoRequest;
import net.javaguides.springboot.todos.response.TodoResponse;
import net.javaguides.springboot.todos.service.TodoService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * TodoController
 * <p>
 * Created by IntelliJ, Spring Framework Guru.
 *
 * @author architecture - pvraul
 * @version 04/07/2026 - 13:55
 * @since 1.17
 */
@RestController
@RequestMapping("/api/v1/todos")
@Tag(name = "Todo REST API Endpoints", description = "Operations for managing user todos")
@RequiredArgsConstructor
@Slf4j
public class TodoController {

    private final TodoService todoService;

    @Operation(summary = "Create a new todo for user", description = "Creates a new todo for the authenticated user")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public TodoResponse createTodo(@Valid @RequestBody final TodoRequest    todoRequest) {
        log.info("🌐️- Creating a new todo via controller: {}", todoRequest);

        return todoService.createTodo(todoRequest);
    }

}
