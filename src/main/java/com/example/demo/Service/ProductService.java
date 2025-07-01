package com.example.demo.Service;

import com.example.demo.entity.ProductEntity;
import com.example.demo.entity.UserEntity;
import com.example.demo.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository){
        this.productRepository = productRepository;
    }

    //상품 저장
    public void save(ProductEntity product){
        productRepository.save(product);
    }

    //판매자 상품 가져오기
    public List<ProductEntity> findBySeller(UserEntity seller){
        return productRepository.findBySeller(seller);
    }

    //판매 물품 ID로 검색하기
    public ProductEntity findById(Long id) {
        return productRepository.findById(id).orElse(null);
    }

}
