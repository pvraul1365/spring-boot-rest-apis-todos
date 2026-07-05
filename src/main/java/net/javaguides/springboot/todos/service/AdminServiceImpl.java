package net.javaguides.springboot.todos.service;

import java.util.List;
import java.util.stream.StreamSupport;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.javaguides.springboot.todos.entity.Authority;
import net.javaguides.springboot.todos.entity.User;
import net.javaguides.springboot.todos.repository.UserRepository;
import net.javaguides.springboot.todos.response.UserResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
