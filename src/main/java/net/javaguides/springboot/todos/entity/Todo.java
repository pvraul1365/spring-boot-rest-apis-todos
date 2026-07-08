package net.javaguides.springboot.todos.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Todo
 * <p>
 * Created by IntelliJ, Spring Framework Guru.
 *
 * @author architecture - pvraul
 * @version 25/06/2026 - 08:22
 * @since 1.17
 */

@Document(collection = "todos")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Todo {

    @Id
    private String id;

    private String title;

    private String description;

    private int priority;

    private boolean completed;

    @DBRef(lazy = true)
    private User owner;

}
