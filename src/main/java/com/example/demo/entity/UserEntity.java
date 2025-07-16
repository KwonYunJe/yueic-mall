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

    //유저의 역할 필드, 열거형(enum)으로 정의 (관리자, 판매자, 소비자)
    public enum Role{
        ADMIN, SELLER, CUSTOMER
    }

    //PK, 자동 증가
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //제약 조건 : 비움 금지, 중복 금지
    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false, unique = true)
    private String nickname;

    //제약 조건 : 비움 금지
    @Column(nullable = false)
    private String password;

    //제약 조건 : 비움 금지, 중복 금지
    @Column(nullable = false, unique = true)
    private String email;

    //enum 값을 DB에 어떻게 저장할지 지정, 아래는 문자열로 저장함을 의미.
    @Enumerated(EnumType.STRING)
    @Column
    private Role role;
}


