package net.javaguides.springboot.todos.service;

import net.javaguides.springboot.todos.request.TodoRequest;
import net.javaguides.springboot.todos.response.TodoResponse;

public interface TodoService {

    TodoResponse createTodo(TodoRequest todoRequest);

}
