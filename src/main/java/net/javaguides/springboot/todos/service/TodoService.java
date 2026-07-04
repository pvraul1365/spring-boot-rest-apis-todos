package net.javaguides.springboot.todos.service;

import java.util.List;
import net.javaguides.springboot.todos.request.TodoRequest;
import net.javaguides.springboot.todos.response.TodoResponse;

public interface TodoService {

    TodoResponse createTodo(TodoRequest todoRequest);

    List<TodoResponse> getAllTodos();

    TodoResponse toggleTodoCompletion(long id);

    void deleteTodo(long id);
}
