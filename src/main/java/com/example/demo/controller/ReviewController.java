package com.example.demo.controller;

import com.example.demo.Service.ReviewService;
import com.example.demo.dto.review.ReviewDto;
import com.example.demo.entity.User;
import com.example.demo.repository.ReviewRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/reviews")
public class ReviewController {

    private final ReviewService reviewService;
    private final ReviewRepository reviewRepository;

    public ReviewController(ReviewService reviewService, ReviewRepository reviewRepository) {
        this.reviewService = reviewService;
        this.reviewRepository = reviewRepository;
    }

    @PostMapping("/write")
    public String submitReview(@ModelAttribute ReviewDto dto, HttpSession session) {
        User loginUser = (User) session.getAttribute("loginUser");
        if (loginUser == null) return "redirect:/user/login";

        reviewService.createReview(loginUser, dto);
        return "redirect:/products/product/" + dto.getProductId(); // 다시 상세 페이지로
    }
}