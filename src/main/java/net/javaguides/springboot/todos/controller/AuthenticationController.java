package net.javaguides.springboot.todos.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.javaguides.springboot.todos.request.AuthenticationRequest;
import net.javaguides.springboot.todos.request.RegisterRequest;
import net.javaguides.springboot.todos.response.AuthenticationResponse;
import net.javaguides.springboot.todos.service.AuthenticationService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * AuthenticationController
 * <p>
 * Created by IntelliJ, Spring Framework Guru.
 *
 * @author architecture - pvraul
 * @version 30/06/2026 - 19:16
 * @since 1.17
 */
@RestController
@RequestMapping("/api/v1/auth")
@Tag(name = "Authentication REST API Endpoints", description = "Operations related to register & login")
@RequiredArgsConstructor
@Slf4j
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @Operation(summary = "Register a new user", description = "This endpoint allows you to register a new user by providing their first name, last name, email, and password.")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/register")
    public void register(@Valid @RequestBody final RegisterRequest registerRequest) {
        log.info("🌐 - Register request received");
        this.authenticationService.register(registerRequest);
        log.info("✅ - User registered successfully: {}", registerRequest.getEmail());
    }

    @Operation(summary = "Login a user", description = "This endpoint allows a user to login by providing their email and password.")
    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/login")
    public AuthenticationResponse login(@Valid @RequestBody final AuthenticationRequest authenticationRequest) {
        log.info("🌐 - Login request received");
        AuthenticationResponse response = this.authenticationService.login(authenticationRequest);
        log.info("✅ - User logged in successfully: {}", authenticationRequest.getEmail());
        return response;
    }
}
