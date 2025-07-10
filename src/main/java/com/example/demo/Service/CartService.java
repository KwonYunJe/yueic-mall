package com.example.demo.Service;

import com.example.demo.cart.CartItem;
import com.example.demo.entity.CartItemEntity;
import com.example.demo.entity.ProductEntity;
import com.example.demo.entity.UserEntity;
import com.example.demo.repository.CartItemRepository;
import com.example.demo.repository.ProductRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CartService {
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;

    //생성자
    public CartService(CartItemRepository cartItemRepository, ProductRepository productRepository) {
        this.cartItemRepository = cartItemRepository;
        this.productRepository = productRepository;
    }

    //카트 목록 가져오기
    public List<?> getCartItems(UserEntity user, HttpSession session) {
        //비회원이면 세션에 있는 카트를 가져옴
        if (user == null) {
            return (List<CartItem>) Optional.ofNullable(session.getAttribute("cart")).orElse(new ArrayList<>());
        //회원이면 DB에 있는 카트를 가져옴
        } else {
            return cartItemRepository.findByUser(user);
        }
    }

    //카트에 물건 추가
    public void addToCart(UserEntity user, Long productId, int quantity, HttpSession session) {
        //동일한 물건이 카트에 있는지 확인
        ProductEntity product = productRepository.findById(productId).orElse(null);
        if (product == null) {
            return;
        }

        //로그인 하지 않은 상태라면
        if (user == null) {
            //세션에서 카트를 가져온다. 비었으면 새로운 리스트를 생성한다.
            List<CartItem> cartItems = (List<CartItem>) Optional.ofNullable(session.getAttribute("cart")).orElse(new ArrayList<>());

            //추가하려는 상품이 카트에 담겨있는지 알려주는 플래그
            boolean found = false;
            //추가하려는 상품이 카트에 존재하는지 확인
            for (CartItem cartItem : cartItems) {
                if (cartItem.getProduct().getId().equals(product.getId())) {
                    //동일한 항목이 존재하면 수량만 ++
                    cartItem.setQuantity(cartItem.getQuantity() + quantity);
                    found = true;
                    break;
                }
            }

            //카트에 추가하려는 상품이 없으면 새로 객체 추가
            if (!found) {
                cartItems.add(new CartItem(product, quantity));
            }

            //상품 객체 리스트를 세션에 저장
            session.setAttribute("cart", cartItems);

        } else {
            // 로그인한 경우 세션에서 장바구니를 병합하지 않음 — 로그인 시 별도 처리
            // user와 product를 기준으로 검색한다.
            // isPresentOrElse : 값이 있을 때와 없을 때를 나눠서 처리할 수 있다.
            cartItemRepository.findByUserAndProduct(user, product)
                    .ifPresentOrElse(
                            //검색 값이 있는 경우 (동일한 상품이 카트에 담겨있을 경우)
                            item -> {
                                item.setQuantity(item.getQuantity() + quantity);
                                cartItemRepository.save(item);
                            },
                            //값이 없는 경우
                            () -> {
                                //객체를 새로 생성 후 카트에 담으려는 상품의 정보를 저장한다.
                                CartItemEntity newItem = new CartItemEntity();
                                newItem.setUser(user);
                                newItem.setProduct(product);
                                newItem.setQuantity(quantity);
                                cartItemRepository.save(newItem);
                            }
                    );
        }
    }

    //카트 비우기
    public void clearCart(UserEntity user, HttpSession session) {
        //로그인 한 상태면 DB를 지운다.
        //로그인 한 상태가 아니라면 세션을 비운다.
        if (user != null) {
            cartItemRepository.deleteByUser(user);
        } else {
            session.removeAttribute("cart");
        }
    }
}
