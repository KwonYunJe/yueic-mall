package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Review {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //유저 특정
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    //상품 특정
    @ManyToOne(fetch = FetchType.LAZY)
    private Product product;

    //리뷰 내용
    @Column(nullable = false, length = 1000)
    private String content;

    //평점
    @Column(nullable = false)
    private int rating;

    //작성시간 및 수정 시간
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
