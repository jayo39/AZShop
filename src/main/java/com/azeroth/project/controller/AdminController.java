package com.azeroth.project.controller;

import com.azeroth.project.domain.CartProduct;
import com.azeroth.project.domain.SalesDomain;
import com.azeroth.project.domain.UserDomain;
import com.azeroth.project.domain.UserTotal;
import com.azeroth.project.service.AdminService;
import com.azeroth.project.service.CartService;
import com.azeroth.project.service.UserService;
import com.azeroth.project.util.U;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    CartService cartService;
    @Autowired
    UserService userService;
    @Autowired
    AdminService adminService;

    @GetMapping("/portal")
    public void admin(Model model) {
        UserDomain user = U.getLoggedUser();
        List<UserDomain> users = userService.getAllUsers();
        List<CartProduct> cartProducts = cartService.getCart(user.getId());
        model.addAttribute("user", user);
        model.addAttribute("users", users);
        model.addAttribute("cartProducts", cartProducts);
    }

    @GetMapping("/user")
    public void userInfo(@RequestParam("username") String username, Model model) {
        List<SalesDomain> salesList = userService.getSalesByUsername(username);
        model.addAttribute("salesList", salesList);
        model.addAttribute("username", username);
    }

    @GetMapping("/summary")
    public void summary(Model model) {
        List<UserTotal> userTotals = adminService.getUserTotal();
        model.addAttribute("userTotals", userTotals);
    }
}
