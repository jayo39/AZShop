package com.azeroth.project.service;

import com.azeroth.project.domain.CartDomain;
import com.azeroth.project.domain.CartProduct;

import java.util.List;

public interface CartService {
    int addCart(CartDomain cart);

    List<CartProduct> getCart(Long user_id);

    int deleteCart(Long id, Long product_id);

    Long getAmount(Long user_id, Long product_id);

    int modifyAmount(Long user_id, Long product_id, Long amount);
}
