package com.example.demo.repository;

import com.example.demo.entity.CartItemEntity;
import com.example.demo.entity.ProductEntity;
import com.example.demo.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CartItemRepository extends JpaRepository<CartItemEntity, Long> {
    List<CartItemEntity> findByUser(UserEntity user);
    void deleteByUser(UserEntity user);
    Optional<CartItemEntity> findByUserAndProduct(UserEntity user, ProductEntity product);
}
