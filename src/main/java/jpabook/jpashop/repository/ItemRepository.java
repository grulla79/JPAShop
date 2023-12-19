package jpabook.jpashop.repository;

import jakarta.persistence.EntityManager;
import jpabook.jpashop.domain.Category;
import jpabook.jpashop.domain.Item.Item;
import jpabook.jpashop.domain.Review;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ItemRepository {

    private final EntityManager em;

    public void save(Item item){
        if(item.getId() == null){
            em.persist(item);
        }else {
            //이미 아이템이 존재하는 경우, 아이템의 값을 수정
            //잘 사용하지 않는 방법
            em.merge(item);
        }
    }

    public Item findOne(Long id){
        return em.find(Item.class, id);
    }

    public List<Item> findAll(){
        return em.createQuery("select i from  Item i", Item.class)
                .getResultList();
    }

    public void deleteOne(Long id) {
        em.remove(em.find(Item.class, id));
    }



    public void saveReview(Review review){
        if(review.getId() == null){
            em.persist(review);
        }else {
            //이미 아이템이 존재하는 경우, 아이템의 값을 수정
            //잘 사용하지 않는 방법
            em.merge(review);
        }
    }

    public List<Review> findReview(Long id){
        return em.createQuery("select r from  Review r where item_id = " + id, Review.class)
                .getResultList();
    }
}
