package com.example.demo.dto.review;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

public record ReviewDto(
        Long reviewId,
        @NotNull Long productId,
        @NotNull Integer rating,
        @NotBlank @Size(max = 1000) String content,
        LocalDateTime createdAt,
        Long userId,
        String nickname  // User의 표시용 필드(프로젝트에 맞게 변경)
) {}