package com.example.finalprojectdtomarket.product;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductJPARepository extends JpaRepository<Product, Integer> {

    @Query("select p from Product p where p.name like %:keyword% order by p.id DESC")
    List<Product> findKeyword(@Param("keyword") String keyword);

    @Modifying(clearAutomatically = true)
    @Query("delete from Product p where p.id = :productId")
    void deleteByProductId(@Param("productId") Integer productId);

    @Query("select p from Product p join fetch p.category c where p.id = :productId")
    Product findByIdWithCategory(@Param("productId") Integer productId);


}
