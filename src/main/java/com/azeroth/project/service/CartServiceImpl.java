package com.azeroth.project.service;

import com.azeroth.project.domain.CartDomain;
import com.azeroth.project.domain.CartProduct;
import com.azeroth.project.repository.CartRepository;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartServiceImpl implements CartService {

    private CartRepository cartRepository;

    @Autowired
    public CartServiceImpl(SqlSession sqlSession) {
        cartRepository = sqlSession.getMapper(CartRepository.class);
    }

    @Override
    public int addCart(CartDomain cart) {
        return cartRepository.addCart(cart);
    }

    @Override
    public List<CartProduct> getCart(Long user_id) {
        return cartRepository.getCart(user_id);
    }

    @Override
    public int deleteCart(Long id, Long product_id) {
        if(id != null) {
            return cartRepository.deleteCart(id);
        }
        return cartRepository.deleteCartByProduct(product_id);
    }

    @Override
    public Long getAmount(Long user_id, Long product_id) {
        return cartRepository.getAmount(user_id, product_id);
    }

    @Override
    public int modifyAmount(Long user_id, Long product_id, Long amount) {
        return cartRepository.modifyAmount(user_id, product_id, amount);
    }
}
