package com.example.demo.cart;

import com.example.demo.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CartItem {
    private Product product;
    private int quantity;
}
