package com.example.finalprojectdtomarket.code;

import jakarta.persistence.Id;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CategoryJPARepository extends JpaRepository<Category, Integer> {

    @Query("select c from Category c where c.categoryCode = :categoryCode")
    Optional<Category> findByCodeName(@Param("categoryCode") String categoryCode);
}
