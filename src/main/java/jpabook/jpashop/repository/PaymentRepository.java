package jpabook.jpashop.repository;

import jakarta.persistence.EntityManager;
import jpabook.jpashop.domain.Payment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class PaymentRepository {

    private final EntityManager em;

    public List<Payment> findAll(){
        return em.createQuery("select p from  Payment p", Payment.class)
                .getResultList();
    }

}

