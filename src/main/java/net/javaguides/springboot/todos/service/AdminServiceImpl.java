package net.javaguides.springboot.todos.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.javaguides.springboot.todos.entity.Authority;
import net.javaguides.springboot.todos.entity.User;
import net.javaguides.springboot.todos.repository.UserRepository;
import net.javaguides.springboot.todos.response.UserResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

/**
 * AdminServiceImpl
 * <p>
 * Created by IntelliJ, Spring Framework Guru.
 *
 * @author architecture - pvraul
 * @version 05/07/2026 - 12:35
 * @since 1.17
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public List<UserResponse> getAllUsers() {
        log.info("📤 - AdminServiceImpl.getAllUsers() called");
        return StreamSupport.stream(this.userRepository.findAll().spliterator(), false)
                .map(this::convertToUserResponse)
                .toList();
    }

    @Transactional
    @Override
    public UserResponse promoteToAdmin(final long id) {
        log.info("📤 - AdminServiceImpl.promoteToAdmin() called with id: {}", id);

        Optional<User> user = this.userRepository.findById(id);

        if (user.isEmpty() || user.get().getAuthorities()
                .stream()
                .anyMatch(auth -> "ROLE_ADMIN".equals(auth.getAuthority()))) {
            log.warn("⚠️ - User with id: {} not found or already an admin", id);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User not found or already an admin");
        }

        List<Authority> authorities = new ArrayList<>();
        authorities.add(new Authority("ROLE_ADMIN"));
        authorities.add(new Authority("ROLE_EMPLOYEE"));
        user.get().setAuthorities(authorities);

        User savedUser = this.userRepository.save(user.get());

        log.info("✅ - User with id: {} promoted to admin successfully", id);

        return this.convertToUserResponse(savedUser);
    }

    private UserResponse convertToUserResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .fullName(user.getFirstName() + " " + user.getLastName())
                .email(user.getEmail())
                .authorities(user.getAuthorities()
                        .stream()
                        .map(auth -> (Authority) auth).toList()
                )
                .build();
    }
}
