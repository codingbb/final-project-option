package com.example.finalprojectdtomarket.cart;

import com.example.finalprojectdtomarket.option.Option;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CartJPARepository extends JpaRepository<Cart, Integer> {

    @Query("select c from Cart c join fetch c.option o join fetch o.product p where c.user.id = :sessionUserId order by c.id desc")
    List<Cart> findByCartUserId(@Param("sessionUserId") Integer sessionUserId);

    @Query("select c from Cart c where c.user.id = :sessionUserId and c.option.id = :optionId")
    Cart findByUserAndOption(@Param("sessionUserId") Integer sessionUserId, @Param("optionId") Integer optionId);
}
