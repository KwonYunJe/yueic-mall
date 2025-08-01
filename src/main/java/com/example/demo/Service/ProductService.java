package com.example.demo.Service;

import com.example.demo.entity.Product;
import com.example.demo.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository){
        this.productRepository = productRepository;
    }

    //모든 상품 가져오기
    public List<Product> findAll(){
        return productRepository.findAll();
    }

    //상품 저장
    public void save(Product product){
        productRepository.save(product);
    }

    //판매자 상품 가져오기
    public List<Product> findBySellerId(Long sellerId){
        return productRepository.findBySellerId(sellerId);
    }

    //판매 물품 ID로 검색하기
    public Product findById(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    //검색, 정렬
    public List<Product> search(String query, String sort, Long sellerId) {
        if (sellerId != null) {
            switch (sort) {
                case "priceAsc":
                    return productRepository.findBySellerIdAndNameContainingOrderByPriceAsc(sellerId, query);
                case "priceDesc":
                    return productRepository.findBySellerIdAndNameContainingOrderByPriceDesc(sellerId, query);
                case "oldest":
                    return productRepository.findBySellerIdAndNameContainingOrderByCreateTimeAsc(sellerId, query);
                default: // 최신순
                    return productRepository.findBySellerIdAndNameContainingOrderByCreateTimeDesc(sellerId, query);
            }
        } else {
            switch (sort) {
                case "priceAsc":
                    return productRepository.findByNameContainingOrderByPriceAsc(query);
                case "priceDesc":
                    return productRepository.findByNameContainingOrderByPriceDesc(query);
                case "oldest":
                    return productRepository.findByNameContainingOrderByCreateTimeAsc(query);
                default: // 최신순
                    return productRepository.findByNameContainingOrderByCreateTimeDesc(query);
            }
        }
    }

}
