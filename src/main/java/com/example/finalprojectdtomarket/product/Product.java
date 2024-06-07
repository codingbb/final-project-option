package com.example.finalprojectdtomarket.product;

import com.example.finalprojectdtomarket.category.Category;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

@NoArgsConstructor
@Data
@Table(name = "product_tb")
@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true, length = 20, nullable = false)
    private String name;             // 상품명

    @Column(nullable = false)
    private Integer price;           // 기본 상품 가격만 나옴

//    @Column(nullable = false)
//    private Integer qty;             // 상품 재고

//    @Column(nullable = false)
//    private String img;              // 상품이미지

    //code 추가
    @ManyToOne(fetch = FetchType.LAZY)
    private Category category;

    @CreationTimestamp
    private Timestamp createdAt;

    @Builder
    public Product(Integer id, String name, Integer price, Category category, Timestamp createdAt) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.category = category;
        this.createdAt = createdAt;
    }
}
