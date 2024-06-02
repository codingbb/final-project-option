package com.example.finalprojectdtomarket.user;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;

@NoArgsConstructor
@Data
@Table(name = "user_tb")
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false) //널 값 허용 안하는 어노테이션
    private String password;  //패스워드

    @Column(unique = true)
    private String username;  //아이디

    @Column(nullable = false)
    private String personName;  //성함

    @Column(nullable = false)
    private String phone;     //폰번호

    @Column(nullable = false)
    private String email;     //이메일

    @Column(nullable = false)
    private LocalDate birth;       //생년월일

    @Column
    private Integer age;

    @Column
    private Integer role;     // 1 -> admin, 2 -> user

    @Column
    private String gender;  // male / female

    @CreationTimestamp // pc -> db (날짜주입)
    private Timestamp createdAt;

    @Builder
    public User(Integer id, String password, String username, String phone, String email, Integer age, LocalDate birth, Integer role, String personName, String gender, Timestamp createdAt) {
        this.id = id;
        this.password = password;
        this.username = username;
        this.phone = phone;
        this.email = email;
        this.age = age;
        this.birth = birth;
        this.role = role;
        this.personName = personName;
        this.gender = gender;
        this.createdAt = createdAt;
    }
}
