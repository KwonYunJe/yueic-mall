package com.example.demo.controller;

import com.example.demo.Service.ProductService;
import com.example.demo.entity.ProductEntity;
import com.example.demo.entity.UserEntity;
import com.example.demo.repository.ProductRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/seller")
public class SellerController {

    private final ProductService productService;
    private final ProductRepository productRepository;

    //상품 레포지토리, 서비스 생성자
    public SellerController(ProductService productService, ProductRepository productRepository) {
        this.productService = productService;
        this.productRepository = productRepository;
    }

    //판매자 로그인 후 판매자 대시보드로 이동
    @GetMapping("/dashboard")
    public String showDashboard(HttpSession httpSession, Model model){
        UserEntity loginUser = (UserEntity) httpSession.getAttribute("loginUser");

        if(loginUser == null || loginUser.getRole() != UserEntity.Role.SELLER){
            return "redirect:/";
        }

        model.addAttribute("sellerName", loginUser.getUsername());
        return "seller/dashboard";
    }


    //상품 등록 폼
    @GetMapping("/product/create")
    public String showCreateProductFor(Model model){
        model.addAttribute("/product", new ProductEntity());
        return "seller/createProduct";
    }

    //상품 등록 실행
    @PostMapping("/product/create")
    public String createProduct(@ModelAttribute ProductEntity productEntity, HttpSession httpSession){
        UserEntity seller = (UserEntity) httpSession.getAttribute("loginUser");
        if(seller == null || seller.getRole() != UserEntity.Role.SELLER){
            return "redirect:/";
        }

        productEntity.setSeller(seller);
        productRepository.save(productEntity);

        return "redirect:/seller/dashboard";
    }

    //판매자 본인이 올린 상품 목록
    @GetMapping("product/list")
    public String showsProductList(HttpSession httpSession, Model model){
        UserEntity seller = (UserEntity) httpSession.getAttribute("loginUser");

        if(seller == null || seller.getRole() != UserEntity.Role.SELLER){
            return "redirect:/";
        }

        List<ProductEntity> products = productService.findBySeller(seller);
        model.addAttribute("products", products);

        return  "seller/productList";
    }

    //판매중인 상품 수정 폼으로 이동
    @GetMapping("/product/edit/{id}")
    public String showEditProductForm(@PathVariable Long id, HttpSession httpSession, Model model){
        UserEntity seller = (UserEntity) httpSession.getAttribute("loginUser");
        if(seller == null || seller.getRole() != UserEntity.Role.SELLER){
            return "redirect:/";
        }

        //판매 물품 정보 가져오기
        ProductEntity product = productService.findById(id);

        //판매 물품의 정보가 없거나 판매자의 ID가 일치하지 않는 경우(판매자의 물건이 아닐 경우)
        if(product == null || !product.getSeller().getId().equals(seller.getId())){
            System.out.println("판매 상품이 없거나 일치하지 않는 판매자.");
            return "redirect:/seller/product/list";
        }

        model.addAttribute("product", product);
        return "seller/editProduct";
    }

    //폼에 입력된 수정사항을 전송
    @PostMapping("/product/edit/{id}")
    public String editProduct(@PathVariable Long id,
                              @ModelAttribute ProductEntity formProduct,
                              HttpSession httpSession){
        UserEntity seller = (UserEntity) httpSession.getAttribute("loginUser");

        if(seller == null || seller.getRole() != UserEntity.Role.SELLER){
            return "redirect:/";
        }

        ProductEntity product = productService.findById(id);

        if(product == null || !product.getSeller().getId().equals(seller.getId())){
            System.out.println("판매 상품이 없거나 일치하지 않는 판매자.");
            return "redirect:/seller/product/list";
        }

        product.setName(formProduct.getName());
        product.setPrice(formProduct.getPrice());

        productService.save(product);

        System.out.println("판매상품 수정 완료");

        return "redirect:/seller/product/list";
    }
}
