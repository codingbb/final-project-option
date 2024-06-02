package com.example.finalprojectdtomarket.cart;

import com.example.finalprojectdtomarket.option.Option;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CartJPARepository extends JpaRepository<Cart, Integer> {

    @Query("select c from Cart c join fetch c.option o where c.user.id = :sessionUserId")
    List<Cart> findByCartUserId(@Param("sessionUserId") Integer sessionUserId);
}
