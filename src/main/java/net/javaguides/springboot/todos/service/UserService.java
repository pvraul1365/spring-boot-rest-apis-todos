package net.javaguides.springboot.todos.service;

import net.javaguides.springboot.todos.request.PasswordUpdateRequest;
import net.javaguides.springboot.todos.response.UserResponse;

public interface UserService {

    UserResponse getUserInfo();

    void deleteUser();

    void updatePassword(PasswordUpdateRequest passwordUpdateRequest);
}
