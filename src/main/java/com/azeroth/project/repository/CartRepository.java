package com.azeroth.project.repository;

import com.azeroth.project.domain.CartDomain;
import com.azeroth.project.domain.CartProduct;

import java.util.List;

public interface CartRepository {

    int addCart(CartDomain cart);

    List<CartProduct> getCart(Long user_id);

    int deleteCart(Long id);

    int deleteCartByProduct(Long product_id);

    // 카트에 담긴 상품 수량 가져오기
    Long getAmount(Long user_id, Long product_id);

    int modifyAmount(Long user_id, Long product_id, Long amount);
}
