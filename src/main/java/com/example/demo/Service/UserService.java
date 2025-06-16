package com.example.demo.Service;

import com.example.demo.entity.UserEntity;
import com.example.demo.repository.UserRepository;
import org.springframework.stereotype.Service;

//회원가입 로직을 처리하는 계층
@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    //유저 생성///////////////////////////
    //해당 메서드로 유니크 값 중복을 방지한다.
    public String register(UserEntity userEntity){
        if(userRepository.existsByUsername(userEntity.getUsername())){
            System.out.println("아이디 중복");
            return "username duplication";   //아이디 중복 방지
        }

        if(userRepository.existsByEmail(userEntity.getEmail())){
            System.out.println("이메일 중복");
            return "email duplication";
        }
        userRepository.save(userEntity);
        return "Sueccess";
    }

    //로그인 /////////////////////////////
    public UserEntity findByUsername(String username){
        //유저레포지토리의 메서드를 사용
        return userRepository.findByUsername(username);
    }
}
