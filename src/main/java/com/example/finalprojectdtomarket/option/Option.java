package com.example.finalprojectdtomarket.option;

import com.example.finalprojectdtomarket.order.OrderStatus;
import com.example.finalprojectdtomarket.product.Product;
import com.example.finalprojectdtomarket.user.User;
import jakarta.persistence.*;
import lombok.Builder;
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
    private Integer orderQty;
    private Integer price;

    @ManyToOne(fetch = FetchType.LAZY)
    private Product product;

}
