package com.example.demo.controller;

import com.example.demo.Service.ReviewService;
import com.example.demo.dto.review.ReviewDto;
import com.example.demo.entity.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
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
    public String submitReview(@Valid @ModelAttribute ReviewDto dto, HttpSession session, HttpServletRequest request,
                               BindingResult br,
                               RedirectAttributes ra) {
        User loginUser = (User) session.getAttribute("loginUser");

        //비 로그인시 로그인 화면으로 이동
        if (loginUser == null) return "redirect:/user/login";

        //입력값 검증 실패 시 원위치
        //DTO 검증 실패 시 -> 플래시로 에러메시지를 싣고 -> productId가 있으면 해당 상품 상세로, 없으면 이전페이지로 돌려보냄.
        if(br.hasErrors()) {
            ra.addFlashAttribute("errors", "입력값을 확인하세요.");
            if(dto.productId() != null){
                return "redirect:/products/product/"+dto.productId();
            }
            String back = request.getHeader("Referer");
            return "redirect:"+(back != null ? back : "/");
        }

        //리뷰 작성 시도
        try {
            reviewService.create(dto.productId(), loginUser.getId(),
                    dto.rating(), dto.content());
            ra.addFlashAttribute("msg", "리뷰가 등록되었습니다.");
        } catch (IllegalStateException e) { // 중복 작성 등
            ra.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/products/product/" + dto.productId(); // 상세로 복귀
    }

    @PostMapping("/{id}/edit")
    public String editReview(@PathVariable Long id,
                             @RequestParam Integer rating,
                             @RequestParam String content,
                             @RequestParam Long productId,
                             HttpSession session,
                             RedirectAttributes ra) {
        User loginUser = (User) session.getAttribute("loginUser");

        //비 로그인시 로그인창으로 이동
        if(loginUser == null) return "redirect:/user/login";

        //리뷰 수정 시도
        try{
            reviewService.update(id, loginUser.getId(), rating, content);
            ra.addFlashAttribute("msg", "리뷰가 수정되었습니다.");
        }catch (Exception e){
            ra.addFlashAttribute("error", e.getMessage());
        }

        //물건 상세페이지로 이동
        return "redirect:/products/product/" + productId;
    }

    @PostMapping("/{id}/delete")
    public String deleteReview(@PathVariable Long id,
                               @RequestParam Long productId,
                               HttpSession session,
                               RedirectAttributes ra) {
        User loginUser = (User) session.getAttribute("loginUser");

        //비 로그인시 로그인 화면으로 이동
        if(loginUser == null) return "redirect:/user/login";

        //리뷰 삭제 시도
        try{
            reviewService.delete(id, loginUser.getId());
            ra.addFlashAttribute("msg", "리뷰가 삭제되었습니다.");
        } catch (Exception e){
            ra.addFlashAttribute("error", e.getMessage());
        }

        //제품 상세페이지로 이동
        return "redirect:/products/product/" + productId;
    }
}