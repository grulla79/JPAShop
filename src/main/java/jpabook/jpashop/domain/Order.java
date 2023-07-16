package jpabook.jpashop.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter @Setter
public class Order {

    @Id @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    //다대일 관계
    @ManyToOne
    @JoinColumn(name = "member_id") //조인 할 키
    private Member member;

    @OneToMany
    private List<OrderItem> orderItem = new ArrayList<>();

    @OneToOne
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;

    //자동으로 날짜 지원
    private LocalDateTime orderDate;
    @Enumerated(EnumType.STRING)
    private OrderStatus status; //주문상태  [order / cancel]
}
