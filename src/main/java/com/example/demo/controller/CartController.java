package com.example.demo.controller;

import com.example.demo.Service.CartService;
import com.example.demo.entity.CartItem;
import com.example.demo.entity.User;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
        User user = (User) httpSession.getAttribute("loginUser");
        List<CartItem> cartItems = (List<CartItem>) cartService.getCartItems(user, httpSession);

        int totalPrice = cartItems.stream()
                .mapToInt(item -> item.getQuantity() * item.getProduct().getPrice())
                .sum();

        model.addAttribute("cartItems", cartItems);
        model.addAttribute("totalPrice", totalPrice);
        return "customer/cart";
    }

    // 장바구니에 물건 추가
    @PostMapping("/add/{productId}")
    public String addToCart(@PathVariable Long productId,
                            @RequestParam(defaultValue = "1") int quantity,
                            HttpSession httpSession) {
        User user = (User) httpSession.getAttribute("loginUser");

        if (user.getRole() != User.Role.CUSTOMER) {
            return "redirect:/accessDenied";
        }

        cartService.addToCart(user, productId, quantity, httpSession);
        return "redirect:/customer/cart";
    }

    // 카트 비우기
    @PostMapping("/clear")
    public String clearCart(HttpSession httpSession) {
        User user = (User) httpSession.getAttribute("loginUser");
        cartService.clearCart(user, httpSession);
        return "redirect:/customer/cart";
    }
}