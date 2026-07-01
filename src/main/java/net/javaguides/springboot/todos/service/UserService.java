package net.javaguides.springboot.todos.service;

import net.javaguides.springboot.todos.response.UserResponse;

public interface UserService {

    UserResponse getUserInfo();

    void deleteUser();
}
