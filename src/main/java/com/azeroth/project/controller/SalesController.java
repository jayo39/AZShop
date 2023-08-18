package com.azeroth.project.controller;

import com.azeroth.project.domain.*;
import com.azeroth.project.service.CartService;
import com.azeroth.project.service.ProductService;
import com.azeroth.project.service.UserService;
import com.azeroth.project.util.U;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/sales")
public class SalesController {

    @Autowired
    CartService cartService;
    @Autowired
    UserService userService;
    @Autowired
    ProductService productService;

    @PostMapping("/checkout")
    public void checkout(@RequestParam("total") Long total, @RequestParam("productid") Long id, Model model) {
        UserDomain user = U.getLoggedUser();
        QryAddressList addressList = userService.getAddressByUser(user.getId());
        List<CartProduct> cartList = cartService.getCart(user.getId());
        if (id != 0) {
            ProductDomain buyProduct = productService.findById(id);
            model.addAttribute("product", buyProduct);
        }
        model.addAttribute("user", user);
        model.addAttribute("total", total);
        model.addAttribute("addressList", addressList.getList());
        model.addAttribute("cartProducts", cartList);
    }

    @PostMapping("/addHistory")
    @Transactional
    public String purchaseHistory(@RequestParam("total") Long total, @RequestParam("productid") Long id, SalesDomain salesDomain) {
        UserDomain user = U.getLoggedUser();
        List<CartProduct> cartList = cartService.getCart(user.getId());
        if (id == 0 && cartList != null && !cartList.isEmpty()) {
            for(CartProduct cart : cartList) {
                ProductDomain product = productService.findById(cart.getProduct_id());
                Long newStock = product.getStock() - cart.getAmount();
                salesDomain.setP_id(cart.getProduct_id());
                salesDomain.setAmount(cart.getAmount());
                productService.modifyAmount(cart.getProduct_id(), newStock);
                userService.addSale(salesDomain);
                cartService.deleteCart(cart.getId(),null);
            }
        }
        else if (id != 0) {
            ProductDomain product = productService.findById(id);
            Long newStock = product.getStock() - (total / product.getPrice());
            productService.modifyAmount(product.getId(), newStock);
            salesDomain.setP_id(product.getId());
            salesDomain.setAmount(total / product.getPrice());
            userService.addSale(salesDomain);
        }
        return "sales/purchaseOk";
    }
}
