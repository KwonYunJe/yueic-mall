package com.example.demo.controller;

import com.example.demo.Service.ProductService;
import com.example.demo.entity.ProductEntity;
import com.example.demo.entity.UserEntity;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    //생성자
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/productsList")
    public String showAllProducts(Model model, HttpSession session){
        List<ProductEntity> productsList = productService.findAll();
        model.addAttribute("products", productsList);
        return "public/productList";
    }
}
