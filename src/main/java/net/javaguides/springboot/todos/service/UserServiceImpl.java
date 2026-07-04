package net.javaguides.springboot.todos.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.javaguides.springboot.todos.entity.Authority;
import net.javaguides.springboot.todos.entity.User;
import net.javaguides.springboot.todos.repository.UserRepository;
import net.javaguides.springboot.todos.request.PasswordUpdateRequest;
import net.javaguides.springboot.todos.response.UserResponse;
import net.javaguides.springboot.todos.util.FindAuthenticatedUser;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

/**
 * UserServiceImpl
 * <p>
 * Created by IntelliJ, Spring Framework Guru.
 *
 * @author architecture - pvraul
 * @version 01/07/2026 - 14:57
 * @since 1.17
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final FindAuthenticatedUser findAuthenticatedUser;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional(readOnly = true)
    public UserResponse getUserInfo() {
        log.info("📤 - UserServiceImpl.getUserInfo() called");

        final User user = findAuthenticatedUser.getAuthenticatedUser();

        return UserResponse.builder()
                .id(user.getId())
                .fullName(user.getFirstName() + " " + user.getLastName())
                .email(user.getEmail())
                .authorities(
                        user.getAuthorities().stream()
                        .map(auth -> (Authority) auth).toList()
                )
                .build();
    }

    @Override
    public void deleteUser() {
        log.info("📤 - UserServiceImpl.deleteUser() called");

        final User user = findAuthenticatedUser.getAuthenticatedUser();
        if (this.isLastAdmin(user)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Admin cannot delete itself");
        }

        userRepository.deleteById(user.getId());
        log.info("🗑️ - UserServiceImpl.deleteUser() - User deleted: {}", user.getEmail());
    }

    @Transactional
    @Override
    public void updatePassword(final PasswordUpdateRequest passwordUpdateRequest) {
        log.info("📤 - UserServiceImpl.updatePassword() called");

        final User user = findAuthenticatedUser.getAuthenticatedUser();

        if (!this.isOldPasswordCorrect(user.getPassword(), passwordUpdateRequest.getOldPassword())) {
            log.error("❌ - UserServiceImpl.updatePassword() - Old password is incorrect for user: {}", user.getEmail());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Old password is incorrect");
        }

        if (!this.isNewPasswordConfirmed(passwordUpdateRequest.getNewPassword(), passwordUpdateRequest.getConfirmNewPassword())) {
            log.error("❌ - UserServiceImpl.updatePassword() - New password and confirm new password do not match for user: {}", user.getEmail());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "New password and confirm new password do not match");
        }

        if (!this.isNewPasswordDifferent(passwordUpdateRequest.getOldPassword(), passwordUpdateRequest.getNewPassword())) {
            log.error("❌ - UserServiceImpl.updatePassword() - New password must be different from the old password for user: {}", user.getEmail());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "New password must be different from the old password");
        }

        user.setPassword(this.passwordEncoder.encode(passwordUpdateRequest.getNewPassword()));
        userRepository.save(user);
        log.info("🔑 - UserServiceImpl.updatePassword() - Password updated for user: {}", user.getEmail());
    }

    private boolean isOldPasswordCorrect(final String currentPassword, final String oldPassword) {
        return this.passwordEncoder.matches(oldPassword, currentPassword);
    }

    private boolean isNewPasswordConfirmed(final String newPassword, final String confirmNewPassword) {
        return newPassword.equals(confirmNewPassword);
    }

    private boolean isNewPasswordDifferent(final String oldPassword, final String newPassword) {
        return !oldPassword.equals(newPassword);
    }

    private boolean isLastAdmin(final User user) {
        boolean isAdmin = user.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"));

        if (isAdmin) {
            long adminCount = userRepository.countAdminUsers();
            if (adminCount <= 1) {
                return true;
            }
        }

        return false;
    }

}
