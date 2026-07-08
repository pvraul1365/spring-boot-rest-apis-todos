package net.javaguides.springboot.todos.repository;

import java.util.Optional;
import net.javaguides.springboot.todos.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface UserRepository extends MongoRepository<User, String> {

    Optional<User> findByEmail(String email);

    @Query("{ 'authorities.authority': 'ROLE_ADMIN' }")
    long countAdminUsers();
}
