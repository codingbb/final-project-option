package com.example.finalprojectdtomarket.image;

import com.example.finalprojectdtomarket.product.Product;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

@NoArgsConstructor
@Data
@Table(name = "image_tb")
@Entity
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String filePath; // 이미지 파일 경로

    @ManyToOne(fetch = FetchType.LAZY)
    private Product product;

    @Builder
    public Image(Integer id, String filePath, Product product) {
        this.id = id;
        this.filePath = filePath;
        this.product = product;
    }


}
