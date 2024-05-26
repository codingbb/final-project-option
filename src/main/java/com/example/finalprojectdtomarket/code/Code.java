package com.example.finalprojectdtomarket.code;

import com.example.finalprojectdtomarket.order.OrderStatus;
import com.example.finalprojectdtomarket.user.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

@NoArgsConstructor
@Data
@Table(name = "code_tb")
@Entity
public class Code {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String codeId; //과일(A01) 채소(A02) 유제품(A03)

    private String codeName;    //과일 채소 유제품

    @CreationTimestamp
    private Timestamp createdAt;

    @Builder
    public Code(Integer id, String codeId, String codeName, Timestamp createdAt) {
        this.id = id;
        this.codeId = codeId;
        this.codeName = codeName;
        this.createdAt = createdAt;
    }
}
