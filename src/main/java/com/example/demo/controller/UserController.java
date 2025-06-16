package com.example.demo.controller;

import com.example.demo.Service.UserService;
import com.example.demo.entity.UserEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    //빈 사용자 객체로 폼 렌더링
    @GetMapping("/register")
    public String showRegisterForm(Model model){
        model.addAttribute("user", new UserEntity());
        return "register";
    }

    //폼 데이터 처리 (성공 or 실패)
    @PostMapping("/register")
    public String register(@ModelAttribute UserEntity userEntity, Model model){
        String result = userService.register(userEntity);
        if(result.equals("username duplication")){
            model.addAttribute("error", "중복된 아이디입니다.");
            model.addAttribute("user", userEntity);
            return "register";
        }else if(result.equals("email duplication")){
            model.addAttribute("error", "이미 가입된 이메일입니다.");
            model.addAttribute("email", userEntity);
        }
        return "redirect:/user/register-success";
    }


    //임시로 회원가입에 성공했을 때 리디렉션을 위한 컨트롤러
    @GetMapping("/register-success")
    public String registerSuccess() {
        return "register-success";  // templates/register-success.html 필요
    }
}
