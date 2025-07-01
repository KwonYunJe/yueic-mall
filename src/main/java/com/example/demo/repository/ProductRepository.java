package com.example.demo.repository;

import com.example.demo.entity.ProductEntity;
import com.example.demo.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
    //판매자 이름으로 상품 가져오기
    List<ProductEntity> findBySeller(UserEntity seller);
}
