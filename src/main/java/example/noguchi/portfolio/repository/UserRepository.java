package example.noguchi.portfolio.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import example.noguchi.portfolio.entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByName(String name);
}