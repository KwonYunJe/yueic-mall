package com.example.demo.dto.review;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ReviewDto {
    private Long productId;

    @NotBlank
    private String content;

    @Min(1)
    @Max(5)
    private int rating;
}