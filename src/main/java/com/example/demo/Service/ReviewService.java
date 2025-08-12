package com.example.demo.Service;

import com.example.demo.entity.Product;
import com.example.demo.entity.Review;
import com.example.demo.entity.User;
import com.example.demo.repository.ProductRepository;
import com.example.demo.repository.ReviewRepository;
import com.example.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service @RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    @Transactional
    public Review create(Long productId, Long userId, int rating, String content) {
        if (reviewRepository.existsByProduct_IdAndUser_Id(productId, userId)) {
            throw new IllegalStateException("이미 이 상품에 리뷰를 작성했습니다.");
        }
        // 추가 쿼리 없이 프록시 참조만 얻고 싶으면 getReferenceById 사용
        Product product = productRepository.getReferenceById(productId);
        User user = userRepository.getReferenceById(userId);

        Review r = Review.builder()
                .product(product)
                .user(user)
                .rating(rating)
                .content(content)
                .build();

        return reviewRepository.save(r);
    }

    @Transactional
    public Review update(Long reviewId, Long userId, int rating, String content) {
        if(rating < 1 || rating > 5) throw new IllegalArgumentException("평점은 1~5점이어야 합니다.");

        Review r = reviewRepository.findById(reviewId).orElseThrow(() -> new IllegalArgumentException("리뷰가 존재하지 않습니다."));

        if(!r.getUser().getId().equals(userId)) {
            throw new SecurityException("작성자만 수정이 가능합니다.");
        }

        r.setRating(rating);
        r.setContent(content);
        return r;
    }

    @Transactional
    public void delete(Long reviewId, Long userId) {
        Review r = reviewRepository.findById(reviewId).orElseThrow(() -> new IllegalArgumentException("리뷰가 존재하지 않습니다."));

        if(!r.getUser().getId().equals(userId)) {
            throw  new SecurityException("작성자만 삭제가 가능합니다.");
        }
        //하드삭제 (소프트 삭제를 원한다면 엔티티에 delete를 만들고 r.setDeleted(true)로 플래그만 변경)
        reviewRepository.delete(r);
    }
}