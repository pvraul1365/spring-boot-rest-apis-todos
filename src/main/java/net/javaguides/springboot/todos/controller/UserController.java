package net.javaguides.springboot.todos.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.javaguides.springboot.todos.entity.User;
import net.javaguides.springboot.todos.response.UserResponse;
import net.javaguides.springboot.todos.service.UserService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * UserController
 * <p>
 * Created by IntelliJ, Spring Framework Guru.
 *
 * @author architecture - pvraul
 * @version 01/07/2026 - 15:05
 * @since 1.17
 */
@Tag(name = "User REST API Endpoints", description = "Operations related to info about current user")
@RestController
@RequestMapping("api/v1/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;

    @GetMapping("/info")
    public UserResponse getUserInfo() {
        log.info("🌐- UserController.getUserInfo() called");
        return userService.getUserInfo();
    }

    @DeleteMapping
    public void deleteUser() {
        log.info("🌐️- UserController.deleteUser() called");
        userService.deleteUser();
    }

}
