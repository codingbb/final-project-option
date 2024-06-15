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

    //oauth
    private String provider;

    @CreationTimestamp // pc -> db (날짜주입)
    private Timestamp createdAt;

    @Builder
    public User(Integer id, String password, String username, String personName, String phone, String email, LocalDate birth, Integer age, Integer role, String gender, String provider, Timestamp createdAt) {
        this.id = id;
        this.password = password;
        this.username = username;
        this.personName = personName;
        this.phone = phone;
        this.email = email;
        this.birth = birth;
        this.age = age;
        this.role = role;
        this.gender = gender;
        this.provider = provider;
        this.createdAt = createdAt;
    }
}
