package net.javaguides.springboot.todos.service;

import jakarta.servlet.http.HttpServletRequest;
import java.util.Map;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * JwtService
 * <p>
 * Created by IntelliJ, Spring Framework Guru.
 *
 * @author architecture - pvraul
 * @version 28/06/2026 - 11:30
 * @since 1.17
 */
public interface JwtService {

    String extractUsername(String token);

    boolean isTokenValid(String token, UserDetails userDetails);

    String generateToken(Map<String, Object> claims, UserDetails userDetails);

}
