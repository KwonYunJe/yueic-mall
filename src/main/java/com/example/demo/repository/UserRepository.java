package com.example.demo.repository;

import com.example.demo.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

//DB 접근을 위한 인터페이스
//상속하는 JpaRepository 는 CRUD를 자동으로 제공한다.
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    //existsByUsername은 사용자 중복 체크용
    boolean existsByUsername(String username);

    boolean existsByEmail(String email);
}
