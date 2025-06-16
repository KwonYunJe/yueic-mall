package com.example.demo.controller;

import com.example.demo.Service.UserService;
import com.example.demo.entity.UserEntity;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/user")
public class UserController {

    //UserService 생성
    private final UserService userService;

    //UserController 생성자
    public UserController(UserService userService){
        this.userService = userService;
    }

    //계정 생성////////////////////////////////////////////////////////////////////////
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
        return "register-success";
        // templates/register-success.html 필요
    }

    //로그인 ///////////////////////////////////////////////////////
    @GetMapping("/login")
    public String showLoginForm(){
        return "login";     //templates 아래의 login
    }

    //HttpSession session : Spring이 자동으로 넘겨주는 세션 객체
    @PostMapping("/login")
    public String login(@RequestParam String username,
                        @RequestParam String password,
                        Model model,
                        HttpSession session){
        UserEntity userEntity = userService.findByUsername(username);

        //반환되는 유저가 존재하고 그 유저의 비밀번호가 입력된 비밀번호와 일치하면
        if(userEntity != null && userEntity.getPassword().equals(password)){
            //로그인 성공 시 사용자 정보를 세션에 저장
            session.setAttribute("loginUser", userEntity);
            return "redirect:/";    //메인페이지로 이동
        }else{
            model.addAttribute("error", "아이디 혹은 비밀번호가 일치하지 않습니다.");
            return "login";
        }
    }

}
