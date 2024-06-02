package com.example.finalprojectdtomarket.order;

import com.example.finalprojectdtomarket.orderItem.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface OrderJPARepository extends JpaRepository<Order, Integer> {

    @Query("select oi from OrderItem oi " +
            "join fetch oi.order o " +
            "join fetch oi.option p " +
            "where o.user.id = :sessionUserId order by o.id desc")
    List<OrderItem> findOrderList(@Param("sessionUserId") Integer sessionUserId);

}
