package net.javaguides.springboot.todos.service;

import net.javaguides.springboot.todos.request.AuthenticationRequest;
import net.javaguides.springboot.todos.request.RegisterRequest;
import net.javaguides.springboot.todos.response.AuthenticationResponse;

public interface AuthenticationService {

    void register(RegisterRequest input);

    AuthenticationResponse login(AuthenticationRequest request);
}
