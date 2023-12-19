package jpabook.jpashop.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Review {


    @Id
    @GeneratedValue
    @Column(name = "review_id")
    private Long id;


    public Long item_id;
    public int point;
    public String memo;

}
