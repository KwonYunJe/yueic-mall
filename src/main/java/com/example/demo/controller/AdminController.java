package com.example.demo.controller;

import com.example.demo.entity.UserEntity;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @GetMapping("/dashboard")
    public String showDashboard(HttpSession httpSession, Model model){
        UserEntity loginUser = (UserEntity) httpSession.getAttribute("loginUser");

        if(loginUser == null || loginUser.getRole() != UserEntity.Role.ADMIN){
            return  "redirect:/";
        }

        model.addAttribute("adminName", loginUser.getUsername());
        return "admin/dashboard";
    }
}
