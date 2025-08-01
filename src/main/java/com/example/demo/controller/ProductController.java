package com.example.demo.controller;

import com.example.demo.Service.ProductService;
import com.example.demo.entity.Product;
import com.example.demo.entity.Review;
import com.example.demo.repository.ProductRepository;
import com.example.demo.repository.ReviewRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;
    private final ReviewRepository reviewRepository;

    //생성자
    public ProductController(ProductService productService, ProductRepository productRepository, ReviewRepository reviewRepository) {
        this.productService = productService;
        this.reviewRepository = reviewRepository;
    }

    //상품 목록 불러오기
    @GetMapping("/productsList")
    public String showAllProducts(Model model, HttpSession session){
        List<Product> productsList = productService.findAll();
        model.addAttribute("products", productsList);
        return "public/productList";
    }

    //상품 상세 페이지
    @GetMapping("/product/{id}")
    public String showProductDetail(@PathVariable Long id, Model model){
        Product product = productService.findById(id);
        if(product == null){
            model.addAttribute("found", "not found");
            return "public/productNotFound";
        }else{
            List<Review> reviews = reviewRepository.findByProductIdWithUser(id);
            model.addAttribute("product", product);
            model.addAttribute("reviews", reviews);
            return "public/productDetail";
        }
    }

    //검색, 정렬
    @GetMapping("/search")
    public String showSearchProducts(@RequestParam("query") String query,
                                     @RequestParam(required = false, defaultValue = "latest") String sort,
                                     @RequestParam(required = false) Long sellerId,
                                     Model model){
        List<Product> products = productService.search(query, sort, sellerId);
        model.addAttribute("products", products);
        model.addAttribute("query", query);
        model.addAttribute("sort", sort);
        return "public/productList";
    }
}
