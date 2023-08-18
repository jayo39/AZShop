package com.azeroth.project.controller;

import com.azeroth.project.domain.*;
import com.azeroth.project.service.CartService;
import com.azeroth.project.service.CategoryService;
import com.azeroth.project.service.ProductService;
import com.azeroth.project.util.U;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping
public class HomeController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ProductService productService;

    @Autowired
    private CartService cartService;

    @Autowired
    private Header header;

    @RequestMapping("/")
    public String index(Integer page, Model model) {
        UserDomain user = U.getLoggedUser();
        header.importData(user, model);
        List<ProductDomain> products = productService.listByPagination(page, model);
        model.addAttribute("products", products);
        return "/index";
    }

    @RequestMapping("/category")
    public void category(@RequestParam("maincode") String maincode, @RequestParam("subcode") String subcode, Model model) {
        UserDomain user = U.getLoggedUser();
        header.importData(user, model);
        List<ProductDomain> products = productService.listByCategory(maincode, subcode);
        model.addAttribute("products", products);
    }
}
