package jpabook.jpashop.repository;

import jakarta.persistence.EntityManager;
import jpabook.jpashop.domain.Category;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class CategoryRepository {

    private final EntityManager em;

    public void save(Category category) {
        if (category.getId() == null) {
            em.persist(category);
        } else {
            //이미 아이템이 존재하는 경우, 아이템의 값을 수정
            //잘 사용하지 않는 방법
            em.merge(category);
        }
    }

    public Category findOne(Long id) {
        return em.find(Category.class, id);
    }

    public List<Category> findAll() {
        return em.createQuery("select c from  Category c", Category.class)
                .getResultList();
    }

    public void deleteOne(Long id) {

        em.remove(em.find(Category.class, id));
    }
}
