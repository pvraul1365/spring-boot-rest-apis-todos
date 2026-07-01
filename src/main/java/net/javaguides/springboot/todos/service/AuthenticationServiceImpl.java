package net.javaguides.springboot.todos.service;

import io.jsonwebtoken.Jwts;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.javaguides.springboot.todos.entity.Authority;
import net.javaguides.springboot.todos.entity.User;
import net.javaguides.springboot.todos.repository.UserRepository;
import net.javaguides.springboot.todos.request.AuthenticationRequest;
import net.javaguides.springboot.todos.request.RegisterRequest;
import net.javaguides.springboot.todos.response.AuthenticationResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * AuthenticationServiceImpl
 * <p>
 * Created by IntelliJ, Spring Framework Guru.
 *
 * @author architecture - pvraul
 * @version 30/06/2026 - 18:55
 * @since 1.17
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @Override
    @Transactional
    public void register(final RegisterRequest input) {
        log.info("📥 - Register request received");

        if (this.isEmailTaken(input.getEmail())) {
            throw new IllegalArgumentException("Email is already taken");
        }

        User user = this.buildNewUser(input);
        this.userRepository.save(user);

        log.info("✅ - User registered successfully: {}", user.getEmail());
    }

    @Override
    @Transactional(readOnly = true)
    public AuthenticationResponse login(final AuthenticationRequest request) {
        log.info("📥 - Login request received");

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        User user = this.userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        final String jwtToken = this.jwtService.generateToken(new HashMap<>(), user);
        log.info("✅ - User logged in successfully: {}", user.getEmail());

        return new AuthenticationResponse(jwtToken);
    }

    private User buildNewUser(final RegisterRequest input) {
        User user = User.builder()
                .id(0)
                .firstName(input.getFirstName())
                .lastName(input.getLastName())
                .email(input.getEmail())
                .password(this.passwordEncoder.encode(input.getPassword()))
                .authorities(this.initialAuthority())
                .build();

        return user;
    }

    private List<Authority> initialAuthority() {

        final boolean isFirstUser = this.userRepository.count() == 0;
        List<Authority> authorities = new ArrayList<>();
        authorities.add(new Authority("ROLE_EMPLOYEE"));

        if (isFirstUser) {
            authorities.add(new Authority("ROLE_ADMIN"));
        }

        return authorities;
    }

    private boolean isEmailTaken(final String email) {
        return this.userRepository.findByEmail(email).isPresent();
    }
}
