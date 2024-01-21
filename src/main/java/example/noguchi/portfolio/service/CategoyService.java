
package example.noguchi.portfolio.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import example.noguchi.portfolio.entity.Category;
import example.noguchi.portfolio.repository.CategoryRepository;

@Service
public class CategoyService {

    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoyService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    // カテゴリ一覧
    public List<Category> getAllCategory() {
        return categoryRepository.findAll();
    }

}
