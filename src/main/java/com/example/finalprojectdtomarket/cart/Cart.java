package com.example.finalprojectdtomarket.cart;

import com.example.finalprojectdtomarket.option.Option;
import com.example.finalprojectdtomarket.product.Product;
import com.example.finalprojectdtomarket.user.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

@NoArgsConstructor
@Data
@Table(name = "cart_tb")
@Entity
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // 주문을 여러번 할수 있다.
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    // 여러번 상품을 주문할 수 있다.
//    @ManyToOne(fetch = FetchType.LAZY)
//    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    private Option option;  //이제 product 대신 option

    @Column(nullable = false)
    private Integer orderQty;   // 주문 수량

    @Column
    private Boolean status;  //장바구니 선택 됐나요? 0 -> false, 1 -> true

    @CreationTimestamp
    private Timestamp createdAt;

    @Transient
    private Integer indexNum;   //index 가공용!! db에는 안넣게 했어요

    @Builder
    public Cart(Integer id, User user, Option option, Integer orderQty, Boolean status, Timestamp createdAt, Integer indexNum) {
        this.id = id;
        this.user = user;
        this.option = option;
        this.orderQty = orderQty;
        this.status = status;
        this.createdAt = createdAt;
        this.indexNum = indexNum;
    }
}
