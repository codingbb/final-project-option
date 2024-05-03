package com.example.finalprojectdtomarket.cart;

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
    @ManyToOne(fetch = FetchType.LAZY)
    private Product product;

    @Column
    private String address;     // 배송지

    @Column(nullable = false)
    private Integer orderQty;   // 주문 수량

    @Column
    private Integer totalQty;   // 남은 상품 총 수량

    @CreationTimestamp
    private Timestamp createdAt;

    @Builder
    public Cart(Integer id, User user, Product product, String address, Integer orderQty, Integer totalQty, Timestamp createdAt) {
        this.id = id;
        this.user = user;
        this.product = product;
        this.address = address;
        this.orderQty = orderQty;
        this.totalQty = totalQty;
        this.createdAt = createdAt;
    }
}
