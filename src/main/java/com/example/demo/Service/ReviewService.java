package com.example.demo.Service;

import com.example.demo.dto.review.ReviewDto;
import com.example.demo.dto.review.ReviewResponseDto;
import com.example.demo.entity.Product;
import com.example.demo.entity.Review;
import com.example.demo.entity.User;
import com.example.demo.repository.ProductRepository;
import com.example.demo.repository.ReviewRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final ProductRepository productRepository;

    public ReviewService(ReviewRepository reviewRepository, ProductRepository productRepository) {
        this.reviewRepository = reviewRepository;
        this.productRepository = productRepository;
    }

    public void createReview(User user, ReviewDto dto) {
        Product product = productRepository.findById(dto.getProductId())
                .orElseThrow(() -> new IllegalArgumentException("상품 없음"));

        Review review = new Review();
        review.setUser(user);
        review.setProduct(product);
        review.setContent(dto.getContent());
        review.setRating(dto.getRating());
        review.setCreatedAt(LocalDateTime.now());
        review.setUpdatedAt(LocalDateTime.now());

        reviewRepository.save(review);
    }

    public List<ReviewResponseDto> getReviewsByProductId(Long productId) {
        List<Review> reviews = reviewRepository.findByProductId(productId);
        return reviews.stream()
                .map(r -> new ReviewResponseDto(
                        r.getUser().getNickname(),
                        r.getContent(),
                        r.getRating(),
                        r.getCreatedAt()))
                .toList();
    }
}