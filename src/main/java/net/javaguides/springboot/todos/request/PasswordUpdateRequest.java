package net.javaguides.springboot.todos.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * PasswordUpdateRequest
 * <p>
 * Created by IntelliJ, Spring Framework Guru.
 *
 * @author architecture - pvraul
 * @version 04/07/2026 - 10:58
 * @since 1.17
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PasswordUpdateRequest {

    @NotEmpty(message = "Old password is required")
    @Size(min = 5, max = 30, message = "Old password must be between 5 and 30 characters")
    private String oldPassword;

    @NotEmpty(message = "New password is required")
    @Size(min = 5, max = 30, message = "New password must be between 5 and 30 characters")
    private String newPassword;

    @NotEmpty(message = "Confirm new password is required")
    @Size(min = 5, max = 30, message = "Confirm new password must be between 5 and 30 characters")
    private String confirmNewPassword;
}
