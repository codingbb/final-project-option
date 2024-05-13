package com.example.finalprojectdtomarket.order;

import com.example.finalprojectdtomarket.cart.Cart;
import com.example.finalprojectdtomarket.orderItem.OrderItem;
import com.example.finalprojectdtomarket.product.Product;
import com.example.finalprojectdtomarket.user.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;
import java.util.List;

@NoArgsConstructor
@Data
@Table(name = "order_tb")
@Entity
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @Column
    private String address;     // 배송지

    @Column
    private Integer sum;

    //ORDER_COMPLETE -> 주문완료, ORDER_CANCEL -> 주문취소
    @Column
    @Enumerated(EnumType.STRING)
    private OrderStatus status;      // 주문 상태 -> true: 주문 완료, false: 주문 취소

    @CreationTimestamp
    private Timestamp createdAt;

    // Json  직렬화 터질수도 있어서!! 조심! -> Casecade 영속성 전파
//    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
//    private List<OrderItem> orderItems;
//
//    public void addOrderItem(OrderItem orderItem){
//        this.orderItems.add(orderItem);
//    }

    @Builder
    public Order(Integer id, User user, String address, Integer sum, OrderStatus status, Timestamp createdAt) {
        this.id = id;
        this.user = user;
        this.address = address;
        this.sum = sum;
        this.status = status;
        this.createdAt = createdAt;
    }
}
