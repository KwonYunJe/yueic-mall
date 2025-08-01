package com.example.demo.repository;

import com.example.demo.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByUserId(Long userId);
    List<Review> findByProductId(Long productId);
    // 유저를 즉시 로딩
    @Query("SELECT r FROM Review r JOIN FETCH r.user WHERE r.product.id = :productId")
    List<Review> findByProductIdWithUser(@Param("productId") Long productId);
}
