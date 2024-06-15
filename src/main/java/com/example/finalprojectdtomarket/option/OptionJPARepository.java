package com.example.finalprojectdtomarket.option;

import com.example.finalprojectdtomarket.product.Product;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OptionJPARepository extends JpaRepository<Option, Integer> {


    @Query("select o from Option o where o.product.id = :productId")
    List<Option> findOption(Sort sort, @Param("productId") Integer productId);

    @Modifying(clearAutomatically = true)
    @Query("delete from Option o where o.product.id = :productId")
    void deleteByProductId(@Param("productId") Integer productId);

}
