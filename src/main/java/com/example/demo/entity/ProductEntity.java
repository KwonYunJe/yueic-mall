package com.example.demo.entity;

import jakarta.persistence.*;

@Entity
public class ProductEntity {

    //기본키, IDENTITY 전략 : DB가 자동으로 증가시키는 방식
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //상품명 / 필수
    @Column(nullable = false)
    private String name;

    //가격
    private int price;

    //판매자와의 관계 / 하나의 판매자 (UserEntity)가 여러 개의 상품(ProductEntity)를 가질 수 있음을 의미
    @ManyToOne
    //DB의 외래키 이름을 seller_id로 지정 (ProductEntity라는 테이블에 seller_id라는 외래키 컬럼이 생김
    @JoinColumn(name = "seller_id")
    private UserEntity seller;
}
