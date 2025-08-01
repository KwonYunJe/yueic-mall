package com.example.demo.repository;

import com.example.demo.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    //판매자 이름으로 상품 가져오기
    List<Product> findBySellerId(Long sellerId);

    //상품 등록일 순으로 정렬하여 가져오기
    List<Product> findByNameContainingOrderByPriceAsc(String name);
    List<Product> findByNameContainingOrderByPriceDesc(String name);
    List<Product> findByNameContainingOrderByCreateTimeAsc(String name);
    List<Product> findByNameContainingOrderByCreateTimeDesc(String name);

    List<Product> findBySellerIdAndNameContainingOrderByPriceAsc(Long sellerId, String name);
    List<Product> findBySellerIdAndNameContainingOrderByPriceDesc(Long sellerId, String name);
    List<Product> findBySellerIdAndNameContainingOrderByCreateTimeAsc(Long sellerId, String name);
    List<Product> findBySellerIdAndNameContainingOrderByCreateTimeDesc(Long sellerId, String name);
}
