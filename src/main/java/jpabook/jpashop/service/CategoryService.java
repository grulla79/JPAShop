package jpabook.jpashop.service;

import jpabook.jpashop.domain.Category;
import jpabook.jpashop.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

// 단순히 기능을 위임하는 클래스...

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    @Transactional//read only가 걸려 있으므로, 오버라이드를 걸어서 값이 저장될 수 있도록 함
    public void savedCategory(Category category) {
        categoryRepository.save(category);
    }

    public List<Category> findCategories() {
        return categoryRepository.findAll();
    }

    public Category findOne(Long id) {
        return categoryRepository.findOne(id);
    }

    @Transactional
    public void updateCategory(Long id, String name)
    {
        Category c = categoryRepository.findOne(id);
        c.setId(id);
        c.setName(name);
    }
    @Transactional
    public void deleteCategory(Long id) {
        categoryRepository.deleteOne(id);
    }

}

