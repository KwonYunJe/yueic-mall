package com.example.demo.controller;

import com.example.demo.Service.ReviewService;
import com.example.demo.dto.review.ReviewDto;
import com.example.demo.entity.User;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
@RequestMapping("/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @PostMapping("/write")
    public String submitReview(@ModelAttribute ReviewDto dto, HttpSession session,
                               RedirectAttributes ra) {
        User loginUser = (User) session.getAttribute("loginUser");
        if (loginUser == null) return "redirect:/user/login";

        try {
            reviewService.create(dto.productId(), loginUser.getId(),
                    dto.rating(), dto.content());
            ra.addFlashAttribute("msg", "리뷰가 등록되었습니다.");
        } catch (IllegalStateException e) { // 중복 작성 등
            ra.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/products/product/" + dto.productId(); // 상세로 복귀
    }
}