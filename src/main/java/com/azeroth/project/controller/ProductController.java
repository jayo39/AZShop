package com.azeroth.project.controller;

import com.azeroth.project.domain.*;
import com.azeroth.project.service.CartService;
import com.azeroth.project.service.CategoryService;
import com.azeroth.project.service.ProductService;
import com.azeroth.project.service.ReviewService;
import com.azeroth.project.util.U;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
@Controller
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private ReviewService reviewService;
    @Autowired
    private CartService cartService;
    @Autowired
    private Header header;

    @GetMapping("/add")
    public void add(Model model) {
        List<CategoryDomain> mainCategories = categoryService.findAllMain();
        List<CategoryDomain> subCategories = categoryService.findAllSub();
        List<CategoryDomain> categories = categoryService.findAll();
        model.addAttribute("mainCategories", mainCategories);
        model.addAttribute("subCategories", subCategories);
        model.addAttribute("categories", categories);
    }

    @PostMapping("/add")
    @Transactional
    public String addOk(@RequestParam("upfile") MultipartFile file, @Valid ProductDomain productDomain, BindingResult result, RedirectAttributes attr, Model model) {
        if (result.hasErrors()) {

            attr.addFlashAttribute("p_name", productDomain.getP_name());
            attr.addFlashAttribute("price", productDomain.getPrice());
            attr.addFlashAttribute("main_cate", productDomain.getMain_cate());
            attr.addFlashAttribute("sub_cate", productDomain.getSub_cate());

            List<FieldError> errList = result.getFieldErrors();
            for(FieldError err : errList) {
                attr.addFlashAttribute("error_" + err.getField(), err.getCode());
            }

            return "redirect:/product/add";

        }
        int sqlResult = productService.addProduct(productDomain, file);
        model.addAttribute("sqlResult", sqlResult);
        return "product/addOk";
    }

    @PostMapping("/delete")
    @Transactional
    public String deleteOk(Long id, String originalImage, Model model) {
        cartService.deleteCart(null, id);
        reviewService.delete(null, id);
        int result = productService.delete(id, originalImage);
        model.addAttribute("result", result);
        return "product/deleteOk";
    }

    @GetMapping("/update/{id}")
    public String update(@PathVariable Long id, Model model) {
        List<CategoryDomain> mainCategories = categoryService.findAllMain();
        List<CategoryDomain> subCategories = categoryService.findAllSub();
        List<CategoryDomain> categories = categoryService.findAll();
        model.addAttribute("product", productService.findById(id));
        model.addAttribute("mainCategories", mainCategories);
        model.addAttribute("subCategories", subCategories);
        model.addAttribute("categories", categories);
        return "product/update";
    }

    @PostMapping("/update")
    @Transactional
    public String updateOk(@RequestParam("upfile") MultipartFile file, @RequestParam("originalImage") String originalImage, @Valid ProductDomain productDomain, BindingResult result, Model model, RedirectAttributes attr) {
        if (result.hasErrors()) {
            attr.addFlashAttribute("p_name", productDomain.getP_name());
            attr.addFlashAttribute("price", productDomain.getPrice());
            attr.addFlashAttribute("main_cate", productDomain.getMain_cate());
            attr.addFlashAttribute("sub_cate", productDomain.getSub_cate());

            List<FieldError> errList = result.getFieldErrors();
            for(FieldError err : errList) {
                attr.addFlashAttribute("error_" + err.getField(), err.getCode());
            }
            return "redirect:/product/update/" + productDomain.getId();
        }
        int isDelete = 0;
        if(file == null) isDelete = 1;
        model.addAttribute("result", productService.update(isDelete, originalImage, productDomain, file));

        return "product/updateOk";
    }

    @GetMapping("/detail/{id}")
    public String detail(@PathVariable long id, Model model){
        UserDomain user = U.getLoggedUser();
        header.importData(user, model);
        ProductDomain product = productService.findById(id);
        model.addAttribute("product", product);
        return "product/detail";
    }

    @PostMapping("/search")
    public String search(@RequestParam("searchedValue") String searchedValue, Model model) {
        UserDomain user = U.getLoggedUser();
        header.importData(user, model);
        List<ProductDomain> products = productService.listBySearch(searchedValue);
        model.addAttribute("products", products);
        model.addAttribute("searchedValue", searchedValue);
        return "product/search";
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.setValidator(new ProductValidator());
    }


}
