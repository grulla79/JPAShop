package jpabook.jpashop.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import jpabook.jpashop.domain.Item;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.Review;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ItemRepository {

    private final EntityManager em;

    public void save(Item item) {
        if (item.getId() == null) {
            em.persist(item);
        } else {
            //이미 아이템이 존재하는 경우, 아이템의 값을 수정
            //잘 사용하지 않는 방법
            em.merge(item);
        }
    }

    public Item findOne(Long id) {
        return em.find(Item.class, id);
    }

    public List<Item> findAll() {
        return em.createQuery("select i from  Item i", Item.class)
                .getResultList();
    }

    //쿼리를 동적으로 생성 - 검색어가 없는 경우 전체 상품을,
    // 있는 경우 해당 검색어에 맞는 상품을 조회
    public List<Item> findAll(Item item) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Item> cq = ((CriteriaBuilder) cb).createQuery(Item.class);
        Root<Item> root = cq.from(Item.class);

        // 검색어가 있는 경우에는 where 조건 추가
        if (StringUtils.hasText(item.getName())) {
            cq.where(cb.equal(root.get("name"), item.getName()));
        }

        return em.createQuery(cq).getResultList();
    }

    public void deleteOne(Long id) {
        em.remove(em.find(Item.class, id));
    }


    public void saveReview(Review review) {
        if (review.getId() == null) {
            em.persist(review);
        } else {
            //이미 아이템이 존재하는 경우, 아이템의 값을 수정
            //잘 사용하지 않는 방법
            em.merge(review);
        }
    }

    public List<Review> findReview(Long id) {
        return em.createQuery("select r from  Review r where item_id = " + id, Review.class)
                .getResultList();
    }
}
