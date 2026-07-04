package net.javaguides.springboot.todos.util;

import lombok.extern.slf4j.Slf4j;
import net.javaguides.springboot.todos.entity.User;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

/**
 * FindAuthenticatedUserImpl
 * <p>
 * Created by IntelliJ, Spring Framework Guru.
 *
 * @author architecture - pvraul
 * @version 03/07/2026 - 14:26
 * @since 1.17
 */
@Component
@Slf4j
public class FindAuthenticatedUserImpl implements FindAuthenticatedUser {

    @Override
    public User getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated() || authentication.getName().equals("anonymousUser")) {
            throw new AccessDeniedException("Authentication required");
        }

        log.info("✅ - FindAuthenticatedUserImpl.getAuthenticatedUser() - Authenticated user: {}", authentication.getName());

        return (User) authentication.getPrincipal();
    }

}
