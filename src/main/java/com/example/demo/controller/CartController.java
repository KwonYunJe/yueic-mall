package com.example.demo.controller;

import com.example.demo.Service.CartService;
import com.example.demo.entity.UserEntity;
import com.example.demo.repository.CartItemRepository;
import com.example.demo.repository.ProductRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/customer/cart")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    // 장바구니 조회
    @GetMapping
    public String viewCart(HttpSession httpSession, Model model) {
        UserEntity user = (UserEntity) httpSession.getAttribute("loginUser");
        List<?> cartItems = cartService.getCartItems(user, httpSession);
        model.addAttribute("cartItems", cartItems);
        return "customer/cart";
    }

    // 장바구니에 물건 추가
    @PostMapping("/add/{productId}")
    public String addToCart(@PathVariable Long productId,
                            @RequestParam(defaultValue = "1") int quantity,
                            HttpSession httpSession) {
        UserEntity user = (UserEntity) httpSession.getAttribute("loginUser");

        if(!"CUSTOMER".equals(user.getRole())){
            return "redirect:/accessDenied";
        }

        cartService.addToCart(user, productId, quantity, httpSession);
        return "redirect:/customer/cart";
    }

    // 카트 비우기
    @PostMapping("/clear")
    public String clearCart(HttpSession httpSession) {
        UserEntity user = (UserEntity) httpSession.getAttribute("loginUser");
        cartService.clearCart(user, httpSession);
        return "redirect:/customer/cart";
    }
}