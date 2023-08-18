package com.azeroth.project.controller;

import com.azeroth.project.domain.CartProduct;
import com.azeroth.project.domain.CategoryDomain;
import com.azeroth.project.domain.UserDomain;
import com.azeroth.project.service.CartService;
import com.azeroth.project.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;

@Controller
public class Header {

    @Autowired
    private CategoryService categoryService;
    @Autowired
    private CartService cartService;
    public void importData(UserDomain user, Model model) {
        List<CartProduct> cartProducts = new ArrayList<>();
        if (user != null) {
            cartProducts = cartService.getCart(user.getId());
        }
        List<CategoryDomain> mainCategories = categoryService.findAllMain();
        List<CategoryDomain> subCategories = categoryService.findAllSub();
        List<CategoryDomain> categories = categoryService.findAll();
        model.addAttribute("cartProducts", cartProducts);
        model.addAttribute("mainCategories", mainCategories);
        model.addAttribute("subCategories", subCategories);
        model.addAttribute("categories", categories);
    }
}
