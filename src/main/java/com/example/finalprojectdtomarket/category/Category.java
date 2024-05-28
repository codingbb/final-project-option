package com.example.finalprojectdtomarket.category;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

@NoArgsConstructor
@Data
@Table(name = "category_tb")
@Entity
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

//    private String categoryCode; //과일(A01) 채소(A02) 유제품(A03)

    private String categoryName;    //과일 채소 유제품

    @CreationTimestamp
    private Timestamp createdAt;

    @Builder
    public Category(Integer id, String categoryName, Timestamp createdAt) {
        this.id = id;
        this.categoryName = categoryName;
        this.createdAt = createdAt;
    }
}
