package com.example.finalprojectdtomarket.orderItem;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface OrderItemJPARepository extends JpaRepository<OrderItem, Integer> {

    @Modifying(clearAutomatically = true)
    @Query("delete from OrderItem oi where oi.product.id = :productId")
    void deleteByProductId(@Param("productId") Integer productId);

    @Query("select oi from OrderItem oi where oi.order.id = :orderId")
    List<OrderItem> findByOrderId(@Param("orderId") Integer orderId);
}
