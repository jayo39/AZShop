package com.azeroth.project.controller;

import com.azeroth.project.domain.CartDomain;
import com.azeroth.project.domain.ProductDomain;
import com.azeroth.project.domain.UserDomain;
import com.azeroth.project.service.CartService;
import com.azeroth.project.service.ProductService;
import com.azeroth.project.util.U;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/cart")
public class CartController {

    @Autowired
    CartService cartService;
    @Autowired
    ProductService productService;

    @PostMapping("/add")
    @Transactional
    public String addOk(@RequestParam("product_id") Long product_id, @RequestParam("amount") Long amount, CartDomain cart, Model model) {
        UserDomain user = U.getLoggedUser();
        ProductDomain productDomain = productService.findById(product_id);
        int result = 0;
        Long inCart = 0L;
        Long max = productDomain.getStock();
        if(cartService.getAmount(user.getId(), product_id) != null) {
            inCart = cartService.getAmount(user.getId(), product_id);
        }
        if (inCart != 0) {
            if ((amount + inCart) > max) {
                amount = max;
            } else {
                amount = amount + inCart;
            }
            result = cartService.modifyAmount(user.getId(), product_id, amount);
        } else {
            cart.setProduct_id(product_id);
            cart.setUser_id(user.getId());
            cart.setAmount(amount);
            result = cartService.addCart(cart);
        }
        model.addAttribute("result", result);
        return "cart/addOk";
    }

    @PostMapping("/update")
    @Transactional
    @ResponseBody
    public int updateOk(@RequestParam("product_id") Long product_id, @RequestParam("amount") Long amount) {
        UserDomain user = U.getLoggedUser();
        return cartService.modifyAmount(user.getId(), product_id, amount);
    }

    @PostMapping("/delete")
    @Transactional
    public String deleteOk(Long id, Model model) {
        cartService.deleteCart(id, null);
        return "redirect:/user/cart";
    }
}
