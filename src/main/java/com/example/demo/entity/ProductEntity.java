package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class ProductEntity {

    //기본키, IDENTITY 전략 : DB가 자동으로 증가시키는 방식
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //상품명 / 필수
    @Column(nullable = false)
    private String name;

    //가격
    private int price;

    @Column(name = "thumbnail_url")  // 오타 수정도 포함!
    private String thumbnailUrl;


    //판매자와의 관계 / 하나의 판매자 (UserEntity)가 여러 개의 상품(ProductEntity)를 가질 수 있음을 의미
    @ManyToOne
    //DB의 외래키 이름을 seller_id로 지정 (ProductEntity라는 테이블에 seller_id라는 외래키 컬럼이 생김
    @JoinColumn(name = "seller_id")
    private UserEntity seller;

    //상품 등록 일시
    @CreatedDate
    //수정 불가능
    @Column(updatable = false)
    private LocalDateTime createTime;
}
