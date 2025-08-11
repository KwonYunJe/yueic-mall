package com.example.demo.dto.review;

import java.time.LocalDateTime;

public record ReviewDto(
        Long reviewId,
        Long productId,
        Integer rating,
        String content,
        LocalDateTime createdAt,
        Long userId,
        String nickname  // User의 표시용 필드(프로젝트에 맞게 변경)
) {}