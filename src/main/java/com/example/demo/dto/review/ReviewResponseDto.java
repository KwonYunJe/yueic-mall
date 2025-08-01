package com.example.demo.dto.review;

import java.time.LocalDateTime;

public record ReviewResponseDto(
        String nickname,
        String content,
        int rating,
        LocalDateTime createdAt
) {}
