package com.example.finalprojectdtomarket.option;

import com.example.finalprojectdtomarket.product.Product;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

@NoArgsConstructor
@Data
@Table(name = "option_tb")
@Entity
public class Option {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String optionName;
    private Integer qty;    // 이쪽에 재고가 있어야하지 않을까
//    private Integer orderQty;
    private Integer price;

    @ManyToOne(fetch = FetchType.LAZY)
    private Product product;

    @CreationTimestamp
    private Timestamp createdAt;

}
