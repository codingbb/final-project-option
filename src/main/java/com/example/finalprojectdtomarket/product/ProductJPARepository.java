package com.example.finalprojectdtomarket.product;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductJPARepository extends JpaRepository<Product, Integer> {

    @Query("select p from Product p where p.name like %:keyword% order by p.id DESC")
    List<Product> findKeyword(@Param("keyword") String keyword);

}
