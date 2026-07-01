package net.javaguides.springboot.todos.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.javaguides.springboot.todos.entity.Authority;
import net.javaguides.springboot.todos.entity.User;
import net.javaguides.springboot.todos.repository.UserRepository;
import net.javaguides.springboot.todos.response.UserResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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

    @Override
    @Transactional(readOnly = true)
    public UserResponse getUserInfo() {
        log.info("📤 - UserServiceImpl.getUserInfo() called");

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated() || authentication.getName().equals("anonymousUser")) {
            throw new AccessDeniedException("Authentication required");
        }

        log.info("✅ - UserServiceImpl.getUserInfo() - Authenticated user: {}", authentication.getName());

        User user = (User) authentication.getPrincipal();

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

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated() || authentication.getName().equals("anonymousUser")) {
            throw new AccessDeniedException("Authentication required");
        }

        log.info("✅ - UserServiceImpl.deleteUser() - Authenticated user: {}", authentication.getName());
        User user = (User) authentication.getPrincipal();

        if (this.isLastAdmin(user)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Admin cannot delete itself");
        }

        userRepository.deleteById(user.getId());
        log.info("🗑️ - UserServiceImpl.deleteUser() - User deleted: {}", user.getEmail());
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
