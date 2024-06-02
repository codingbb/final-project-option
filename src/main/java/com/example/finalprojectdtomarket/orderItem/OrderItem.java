package com.example.finalprojectdtomarket.orderItem;

import com.example.finalprojectdtomarket.option.Option;
import com.example.finalprojectdtomarket.order.Order;
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
@Table(name = "order_item_tb")
@Entity
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

//    @ManyToOne(fetch = FetchType.LAZY)
//    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    private Option option;

    @ManyToOne(fetch = FetchType.LAZY)
    private Order order;

    //이거 있어야하지 않을까?? 구매 확정된 순간 orderQty로 상품 재고 차감 해야하니까
    @Column
    private Integer orderQty;

    @CreationTimestamp
    private Timestamp createdAt;

    @Builder
    public OrderItem(Integer id, Option option, Order order, Integer orderQty, Timestamp createdAt) {
        this.id = id;
        this.option = option;
        this.order = order;
        this.orderQty = orderQty;
        this.createdAt = createdAt;
    }
}
