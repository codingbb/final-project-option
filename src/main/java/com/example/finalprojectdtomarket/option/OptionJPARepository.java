package com.example.finalprojectdtomarket.option;

import com.example.finalprojectdtomarket.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OptionJPARepository extends JpaRepository<Option, Integer> {


}
