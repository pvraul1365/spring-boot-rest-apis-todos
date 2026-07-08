package net.javaguides.springboot.todos.response;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.javaguides.springboot.todos.entity.Authority;

/**
 * UserResponse
 * <p>
 * Created by IntelliJ, Spring Framework Guru.
 *
 * @author architecture - pvraul
 * @version 01/07/2026 - 18:27
 * @since 1.17
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponse {

    private String id;
    private String fullName;
    private String email;
    private List<Authority> authorities;

}
