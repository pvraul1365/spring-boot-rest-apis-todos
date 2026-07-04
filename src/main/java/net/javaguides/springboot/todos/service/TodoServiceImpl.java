package net.javaguides.springboot.todos.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.javaguides.springboot.todos.entity.Todo;
import net.javaguides.springboot.todos.entity.User;
import net.javaguides.springboot.todos.repository.TodoRepository;
import net.javaguides.springboot.todos.request.TodoRequest;
import net.javaguides.springboot.todos.response.TodoResponse;
import net.javaguides.springboot.todos.util.FindAuthenticatedUser;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * TodoServiceImpl
 * <p>
 * Created by IntelliJ, Spring Framework Guru.
 *
 * @author architecture - pvraul
 * @version 04/07/2026 - 13:40
 * @since 1.17
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class TodoServiceImpl implements TodoService {

    private final TodoRepository todoRepository;
    private final FindAuthenticatedUser findAuthenticatedUser;

    @Override
    public TodoResponse createTodo(final TodoRequest todoRequest) {
        log.info("📤 - Creating a new todo: {}", todoRequest);

        final User currentUser = findAuthenticatedUser.getAuthenticatedUser();

        Todo todo = Todo.builder()
                .title(todoRequest.getTitle())
                .description(todoRequest.getDescription())
                .priority(todoRequest.getPriority())
                .completed(false)
                .owner(currentUser)
                .build();

        final Todo savedTodo = todoRepository.save(todo);

        final TodoResponse todoResponse = convertToTodoResponse(savedTodo);
        log.info("🗄️ - Todo created successfully: {}", todoResponse);

        return todoResponse;
    }

    @Transactional(readOnly = true)
    @Override
    public List<TodoResponse> getAllTodos() {
        log.info("📥 - Retrieving all todos for the authenticated user");

        final User currentUser = findAuthenticatedUser.getAuthenticatedUser();

        final List<TodoResponse> todoResponses = this.todoRepository.findByOwner(currentUser)
                .stream()
                .map(this::convertToTodoResponse)
                .toList();
        log.info("📋 - Retrieved {} todos for user: {}", todoResponses.size(), currentUser.getUsername());

        return todoResponses;
    }

    private TodoResponse convertToTodoResponse(final Todo todo) {
        return TodoResponse.builder()
                .id(todo.getId())
                .title(todo.getTitle())
                .description(todo.getDescription())
                .priority(todo.getPriority())
                .completed(todo.isCompleted())
                .build();
    }
}
