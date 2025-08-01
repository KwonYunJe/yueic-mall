package com.example.demo.controller;

import com.example.demo.Service.UserService;
import com.example.demo.entity.User;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;

    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/dashboard")
    public String showDashboard(HttpSession httpSession, Model model){
        User loginUser = (User) httpSession.getAttribute("loginUser");

        if(loginUser == null || loginUser.getRole() != User.Role.ADMIN){
            return  "redirect:/";
        }

        model.addAttribute("adminName", loginUser.getUsername());
        return "admin/dashboard";
    }

    //모든 유저 비밀번호 암호화(임시)
    @GetMapping("/encode-passwords")
    public String encodePasswords() {
        userService.encodeAllPasswords();
        return "redirect:/"; // 또는 결과 페이지
    }
}
