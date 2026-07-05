package net.javaguides.springboot.todos.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Min;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.javaguides.springboot.todos.response.UserResponse;
import net.javaguides.springboot.todos.service.AdminService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * AdminController
 * <p>
 * Created by IntelliJ, Spring Framework Guru.
 *
 * @author architecture - pvraul
 * @version 05/07/2026 - 13:12
 * @since 1.17
 */
@Tag(name = "Admin REST API Endpoints", description = "Operations for managing users and their roles")
@RestController
@RequestMapping("/api/v1/admin")
@Slf4j
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @Operation(summary = "Get all users", description = "Retrieves a list of all users in the system")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public List<UserResponse> getAllUsers() {
        log.info("🌐️- AdminController.getAllUsers() called");

        return this.adminService.getAllUsers();
    }

    @Operation(summary = "Promote user to admin", description = "Promotes a user to admin role")
    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{id}/role")
    public UserResponse promoteToAdmin(@PathVariable @Min(1) final long id) {
        log.info("🌐️- AdminController.promoteToAdmin() called with id: {}", id);

        return this.adminService.promoteToAdmin(id);
    }

    @Operation(summary = "Delete user", description = "Deletes a non-admin user")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable @Min(1) final long id) {
        log.info("🌐️- AdminController.deleteUser() called with id: {}", id);

        this.adminService.deleteNonAdminUser(id);
    }
}
