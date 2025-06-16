package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//DB테이블과 1대 1로 매핑되는 클래스
@Entity //해당 어노테이션이 있으면 JPA가 테이블로 매핑함
@Getter @Setter
@NoArgsConstructor
public class UserEntity {

    //PK, 자동 증가
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //제약 조건 : 비움 금지, 중복 금지
    @Column(nullable = false, unique = true)
    private String username;

    //제약 조건 : 비움 금지
    @Column(nullable = false)
    private String password;

    //제약 조건 : 비움 금지, 중복 금지
    @Column(nullable = false, unique = true)
    private String email;
}
