package com.example.demo.repository;

import com.example.demo.entity.ProductEntity;
import com.example.demo.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
    //판매자 이름으로 상품 가져오기
    List<ProductEntity> findBySellerId(Long sellerId);

    //상품 등록일 순으로 정렬하여 가져오기
    List<ProductEntity> findByNameContainingOrderByPriceAsc(String name);
    List<ProductEntity> findByNameContainingOrderByPriceDesc(String name);
    List<ProductEntity> findByNameContainingOrderByCreateTimeAsc(String name);
    List<ProductEntity> findByNameContainingOrderByCreateTimeDesc(String name);

    List<ProductEntity> findBySellerIdAndNameContainingOrderByPriceAsc(Long sellerId, String name);
    List<ProductEntity> findBySellerIdAndNameContainingOrderByPriceDesc(Long sellerId, String name);
    List<ProductEntity> findBySellerIdAndNameContainingOrderByCreateTimeAsc(Long sellerId, String name);
    List<ProductEntity> findBySellerIdAndNameContainingOrderByCreateTimeDesc(Long sellerId, String name);
}
