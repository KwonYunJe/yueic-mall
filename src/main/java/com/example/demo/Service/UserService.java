package com.example.demo.Service;

import com.example.demo.dto.userdto.UserUpdateDto;
import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

//회원가입 로직을 처리하는 계층
@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    //유저 생성///////////////////////////
    //해당 메서드로 유니크 값 중복을 방지한다.
    public String register(User user){
        if(userRepository.existsByUsername(user.getUsername())){
            System.out.println("아이디 중복");
            return "username duplication";   //아이디 중복 방지
        }

        if(userRepository.existsByEmail(user.getEmail())){
            System.out.println("이메일 중복");
            return "email duplication";
        }
        //비밀번호 암호화
        String password = passwordEncoder.encode(user.getPassword());
        user.setPassword(password);
        //user.setRole(User.Role.CUSTOMER);
        userRepository.save(user);
        return "Sueccess";
    }

    @Transactional
    public void encodeAllPasswords(){
        List<User> users = userRepository.findAll();

        for (User user : users) {
            String rawPassword = user.getPassword();

            // 이미 암호화된 비밀번호는 스킵
            if (!rawPassword.startsWith("$2a$")) {
                user.setPassword(passwordEncoder.encode(rawPassword));
            }
        }

        // 변경된 사용자 정보 저장
        userRepository.saveAll(users);
    }


    //로그인 /////////////////////////////
    public User findByUsername(String username){
        //유저레포지토리의 메서드를 사용
        return userRepository.findByUsername(username);
    }

    //유저 정보 업데이트
    public void updateUser(User user, UserUpdateDto dto) {
        if (!passwordEncoder.matches(dto.getCurrentPassword(), user.getPassword())) {
            throw new IllegalArgumentException("현재 비밀번호가 일치하지 않습니다.");
        }

        user.setNickname(dto.getNickname());
        user.setEmail(dto.getEmail());

        if (dto.getNewPassword() != null && !dto.getNewPassword().isBlank()) {
            user.setPassword(passwordEncoder.encode(dto.getNewPassword()));
        }

        userRepository.save(user);
    }
}
