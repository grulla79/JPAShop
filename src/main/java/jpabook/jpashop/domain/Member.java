package jpabook.jpashop.domain;



import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Member {
    @Id  @GeneratedValue
    @Column(name = "member_Id")
    private Long id;

    private String name;

    @Embedded
    private Address address;
    /*
        일대다 관계
        연관관계 주인이 아님을 선언 -> order 테이블에 있는 Member 필드에 의해 매칭 됨을 알림
     */
    @OneToMany(mappedBy = "member")
    private ArrayList<Order> orders = new ArrayList<>();
}
