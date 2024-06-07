package com.example.finalprojectdtomarket.image;

import com.example.finalprojectdtomarket.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ImageJPARepository extends JpaRepository<Image, Integer> {

    @Query("select i from Image i join fetch i.product p where p.id = :productId")
    List<Image> findByProduct(@Param("productId") Integer productId);
}
