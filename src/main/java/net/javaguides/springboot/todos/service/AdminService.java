package net.javaguides.springboot.todos.service;

import java.util.List;
import net.javaguides.springboot.todos.response.UserResponse;

public interface AdminService {

    List<UserResponse> getAllUsers();

}
