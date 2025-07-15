package com.example.demo.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/customer")
public class CustomerController {

    @GetMapping("/dashboard")
    public String showCustomerDashboard(Model model, HttpSession session) {
        // 로그인된 사용자 정보나 장바구니 데이터를 여기에 담을 수 있음
        // 예: model.addAttribute("cartItems", ...);

        return "customer/dashboard";
    }

//    @GetMapping("/cart")
//    public String redirectToCart(){
//        return "redirect:/customer/cart";
//    }
}
