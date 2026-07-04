package net.javaguides.springboot.todos.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.javaguides.springboot.todos.entity.Todo;
import net.javaguides.springboot.todos.entity.User;
import net.javaguides.springboot.todos.repository.TodoRepository;
import net.javaguides.springboot.todos.request.TodoRequest;
import net.javaguides.springboot.todos.response.TodoResponse;
import net.javaguides.springboot.todos.util.FindAuthenticatedUser;
import org.springframework.stereotype.Service;

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

        final TodoResponse todoResponse = TodoResponse.builder()
                .id(savedTodo.getId())
                .title(savedTodo.getTitle())
                .description(savedTodo.getDescription())
                .priority(savedTodo.getPriority())
                .completed(savedTodo.isCompleted())
                .build();
        log.info("🗄️ - Todo created successfully: {}", todoResponse);

        return todoResponse;
    }
}
