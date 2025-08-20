package com.example.demo.controller;

import com.example.demo.Service.CartService;
import com.example.demo.Service.UserService;
import com.example.demo.cart.CartItem;
import com.example.demo.dto.userdto.UserUpdateDto;
import com.example.demo.entity.User;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {

    //UserService 생성
    private final UserService userService;
    private final CartService cartService;
    private final PasswordEncoder passwordEncoder;

    //UserController 생성자
    public UserController(UserService userService, CartService cartService, PasswordEncoder passwordEncoder){
        this.userService = userService;
        this.cartService = cartService;
        this.passwordEncoder = passwordEncoder;
    }

    //계정 생성////////////////////////////////////////////////////////////////////////
    //빈 사용자 객체로 폼 렌더링
    @GetMapping("/register")
    public String showRegisterForm(Model model){
        model.addAttribute("user", new User());
        return "customer/register";
    }

    //폼 데이터 처리 (성공 or 실패)
    @PostMapping("/register")
    public String register(@ModelAttribute User user, Model model){
        String result = userService.register(user);
        if(result.equals("username duplication")){
            model.addAttribute("error", "중복된 아이디입니다.");
            model.addAttribute("user", user);
            return "customer/register";
        }else if(result.equals("email duplication")){
            model.addAttribute("error", "이미 가입된 이메일입니다.");
            model.addAttribute("user", user);
            return "customer/register";
        }
        // 만약 role이 명시되지 않았다면 CUSTOMER로 설정
        if (user.getRole() == null) {
            user.setRole(User.Role.CUSTOMER);
        }
        return "redirect:/user/register-success";
    }


    //임시로 회원가입에 성공했을 때 리디렉션을 위한 컨트롤러
    @GetMapping("/register-success")
    public String registerSuccess() {
        return "public/login";
        // templates/register-success.html 필요
    }

    //로그인 ///////////////////////////////////////////////////////
    @GetMapping("/login")
    public String showLoginForm(){
        new EncoderTest();
        return "public/login";     //public 아래의 login
    }

    public class EncoderTest {
        public static void main(String[] args) {
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            String encoded = encoder.encode("admin123");
            System.out.println(encoded);
        }
    }

    @PostMapping("/login")
    public String login(@RequestParam String username,
                        @RequestParam String password,
                        Model model,
                        HttpSession session) {

        User user = userService.findByUsername(username);

        // 👉 암호화된 비밀번호 비교
        if (user != null && passwordEncoder.matches(password, user.getPassword())) {
            session.setAttribute("loginUser", user);

            if (user.getRole() == User.Role.ADMIN) {
                return "redirect:/admin/dashboard";
            } else if (user.getRole() == User.Role.SELLER) {
                return "redirect:/seller/dashboard";
            } else {
                List<CartItem> sessionCartItems = (List<CartItem>) session.getAttribute("cartItems");
                if (sessionCartItems != null) {
                    cartService.mergeCart(user, sessionCartItems);
                    session.removeAttribute("cartItems");
                }

                System.out.println("입력된 username: " + username);
                System.out.println("입력된 password: " + password);
                System.out.println("DB에서 가져온 userEntity: " + user);
                System.out.println("패스워드 매치 결과: " + passwordEncoder.matches(password, user.getPassword()));
                return "redirect:/products/productsList";
            }
        } else {
            model.addAttribute("error", "아이디 혹은 비밀번호가 일치하지 않습니다.");

            System.out.println("입력된 username: " + username);
            System.out.println("입력된 password: " + password);
            System.out.println("DB에서 가져온 userEntity: " + user);
            System.out.println("패스워드 매치 결과: " + (user != null && passwordEncoder.matches(password, user.getPassword())));

            return "public/login";
        }
    }


    @GetMapping("/dashboard")
    public String moveDashboard(HttpSession session){
        User user = (User) session.getAttribute("loginUser");

        if (user == null) {
            return "redirect:/public/login"; // 로그인 안 되어 있을 경우 처리
        }

        switch (user.getRole()) {
            case ADMIN:
                return "redirect:/admin/dashboard";
            case SELLER:
                return "redirect:/seller/dashboard";
            case CUSTOMER:
                return "redirect:/customer/dashboard";
            default:
                return "/";
        }
    }

    //로그아웃 ////////////////////////////
    @GetMapping("/logout")
    public String logout(HttpSession httpSession){
        httpSession.invalidate();
        return "redirect:/";
    }

    //정보 수정 ///////////////////////////////////////////////////////
    @GetMapping("/edit")
    public String showEditForm(HttpSession session, Model model){
        User user = (User) session.getAttribute("loginUser");

        if (user == null) {
            return "redirect:/user/login"; // 로그인 안 된 경우 로그인 페이지로 이동
        }

        model.addAttribute("user", user);
        return "customer/edit";
    }

    @PostMapping("/edit")
    public String updateUser(@ModelAttribute UserUpdateDto dto, HttpSession session, RedirectAttributes redirectAttributes){
        User user = (User) session.getAttribute("loginUser");

        try{
            userService.updateUser(user, dto);
            redirectAttributes.addFlashAttribute("message", "회원정보가 수정되었습니다.");
            return "redirect:/user/dashboard";
        }catch (IllegalArgumentException e){
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/user/edit";
        }
    }
}
