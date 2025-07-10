package com.example.demo.cart;

import com.example.demo.entity.ProductEntity;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CartItem {
    private ProductEntity product;
    private int quantity;
}
