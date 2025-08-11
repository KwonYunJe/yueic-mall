package com.example.demo.repository;

import com.example.demo.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    // 페이지로 목록 + 작성자 함께 가져오기 (N+1 방지)
    @EntityGraph(attributePaths = "user") // user를 즉시 로딩
    Page<Review> findByProduct_IdOrderByCreatedAtDesc(Long productId, Pageable pageable);

    boolean existsByProduct_IdAndUser_Id(Long productId, Long userId);

    @Query("select avg(r.rating) from Review r where r.product.id = :productId")
    Double findAvgRatingByProductId(Long productId);

    long countByProduct_Id(Long productId);
}