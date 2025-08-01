package com.example.demo.controller;

import com.example.demo.entity.User;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    //홈으로 보내기
    @GetMapping("/")
    //홈으로 이동할 때 세션을 확인해서 사용자의 이름을 가져올 수 있게 한다.
    public String home(HttpSession session, Model model){
        return "redirect:/products/productsList";
    }


}
