package com.example.finalprojectdtomarket.image;

import com.example.finalprojectdtomarket.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ImageJPARepository extends JpaRepository<Image, Integer> {


}
