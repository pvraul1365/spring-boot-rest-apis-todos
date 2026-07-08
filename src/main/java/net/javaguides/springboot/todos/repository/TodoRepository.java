package net.javaguides.springboot.todos.repository;

import java.util.List;
import java.util.Optional;
import net.javaguides.springboot.todos.entity.Todo;
import net.javaguides.springboot.todos.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TodoRepository extends MongoRepository<Todo, String> {

    List<Todo> findByOwner(User owner);

    Optional<Todo> findByIdAndOwner(String id, User owner);

}
