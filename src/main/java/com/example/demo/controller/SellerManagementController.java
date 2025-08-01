package com.example.demo.controller;

import com.example.demo.Service.UserService;
import com.example.demo.entity.User;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/seller")
public class SellerManagementController {

    //userService 생성
    private final UserService userService;
    //userService 생성자
    public SellerManagementController(UserService userService) {
        this.userService = userService;
    }

    //판매자 페이지로 이동시키는 메서드
    @GetMapping("/create")
    public String showCreateSellerForm(HttpSession httpSession, Model model){
        //로그인 한 사용자의 데이터를 가져옴
        User loginUser = (User) httpSession.getAttribute("loginUser");

        //로그인 한 사용자의 데이터가 없거나 관리자(ADMIN)이 아니라면 메인화면으로 이동
        if(loginUser == null || loginUser.getRole() != User.Role.ADMIN){
            return "redirect:/";
        }

        //위 조건문을 넘긴다면 관리자이므로
        model.addAttribute("userEntity", new User());
        return "admin/createSeller";
    }

    //판매자 페이지에서 넘어오는 메서드
    @PostMapping("/create")
    public String createSeller(@ModelAttribute User user, HttpSession httpSession, Model model){
        //판매자를 등록할 때도 한 번 더 관리자인지 확인한다.
        User loginUser = (User) httpSession.getAttribute("loginUser");

        //관리자가 아니거나 로그인한 상태가 아니라면 홈으로 이동
        if(loginUser == null || loginUser.getRole() != User.Role.ADMIN){
            return "redirect:/";
        }

        //이 메서드를 통해서 등록되는 계정은 SELLER로 등록된다.
        user.setRole(User.Role.SELLER);
        //새로 생성될 판매자 계정을 DB에 저장
        userService.register(user);

        //등록이 끝나면 판매자 등록 페이지로 리디렉션한다.
        return "redirect:/admin/seller/create";
    }
}
