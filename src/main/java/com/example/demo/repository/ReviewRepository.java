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

    //동적 정렬 + 페이징
    @EntityGraph(attributePaths = "user") // 작성자 N+1 방지
    Page<Review> findByProduct_Id(Long productId, Pageable pageable);

    boolean existsByProduct_IdAndUser_Id(Long productId, Long userId);

    // 수정/삭제 시 본인 확인용
    Optional<Review> findByIdAndUser_Id(Long reviewId, Long userId);

    @Query("select avg(r.rating) from Review r where r.product.id = :productId")
    Double findAvgRatingByProductId(Long productId);

    long countByProduct_Id(Long productId);
}