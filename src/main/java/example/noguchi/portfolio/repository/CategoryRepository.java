package example.noguchi.portfolio.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import example.noguchi.portfolio.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
}