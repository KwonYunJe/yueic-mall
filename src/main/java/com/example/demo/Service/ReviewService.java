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
}