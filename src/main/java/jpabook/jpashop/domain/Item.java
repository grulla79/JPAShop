package jpabook.jpashop.domain;

import jakarta.persistence.*;
import jpabook.jpashop.domain.Category;
import jpabook.jpashop.exception.NotEnoughException;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
//상속 전략 - 한 테이블에 모두 저장
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype")
public class Item {
    @Id
    @GeneratedValue
    @Column(name = "item_id")
    private Long id;

    private String name;
    private int price;
    private int stockQuantity;
    private String category;


    //비지니스 로직 추가 - 재고수량 증가로직
    public void addStock(int quantity){

        this.stockQuantity += quantity;
    }


    //비지니스 로직 추가 - 재고수량 감소로직
    public void removeStock(int quantity){

        int restStock = this.stockQuantity - quantity;
        if(restStock < 0){
            //NotEnoughException라는 임의의 클래스를 만들어 예외처리
            throw new NotEnoughException("need more stock");
        }
        this.stockQuantity = restStock;
    }
}
