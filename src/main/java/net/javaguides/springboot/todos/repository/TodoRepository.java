package net.javaguides.springboot.todos.repository;

import java.util.List;
import java.util.Optional;
import net.javaguides.springboot.todos.entity.Todo;
import net.javaguides.springboot.todos.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TodoRepository extends CrudRepository<Todo, Long> {

    List<Todo> findByOwner(User owner);

    Optional<Todo> findByIdAndOwner(Long id, User owner);

}
