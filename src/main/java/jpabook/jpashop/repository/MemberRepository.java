package jpabook.jpashop.repository;

import jakarta.persistence.EntityManager;
import jpabook.jpashop.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class MemberRepository {

    private final EntityManager em;

    //저장 로직
    public void save(Member member){
        em.persist(member);
    }

    //단건 조회
    public Member findOne(Long id){

        //find 메소드를 이용하여 id를 통해 조회
        return em.find(Member.class, id);
    }

    //다중조회
    public List<Member> findAll(){

        //쿼리를 작성해서 조회
        List<Member> result = em.createQuery("select m from Member m", Member.class)
                .getResultList();

        return result;
    }

    //다중조회 - name을 통해 조회
    public List<Member> findByName(String name){
        return em.createQuery("select m from Member m where m.name = :name", Member.class)
                .setParameter("name", name)
                .getResultList();
    }

}
