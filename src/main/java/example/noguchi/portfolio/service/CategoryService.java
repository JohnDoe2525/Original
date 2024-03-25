
package example.noguchi.portfolio.service;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import example.noguchi.portfolio.entity.Category;
import example.noguchi.portfolio.repository.CategoryRepository;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    // カテゴリ一覧
    public List<Category> getAllCategory() {
        return categoryRepository.findAll();
    }

}
