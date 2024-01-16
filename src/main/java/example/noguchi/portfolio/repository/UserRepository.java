package example.noguchi.portfolio.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import example.noguchi.portfolio.entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {
}