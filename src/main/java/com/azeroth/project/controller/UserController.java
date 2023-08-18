package com.azeroth.project.controller;

import com.azeroth.project.domain.*;
import com.azeroth.project.service.CartService;
import com.azeroth.project.service.MailService;
import com.azeroth.project.service.UserService;
import com.azeroth.project.util.U;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.xml.crypto.URIDereferencer;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {

    private String code;
    @Autowired
    private Header header;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserService userService;
    @Autowired
    private CartService cartService;
    @Autowired
    private MailService mailService;

    @GetMapping("/login")
    public void login(Model model){
        model.addAttribute("cartProducts", new ArrayList<>());
    }

    @PostMapping("/loginError")
    public String loginError() {
        return "user/login";
    }

    @RequestMapping("/rejectAuth")
    public void rejectAuth() {;}

    @GetMapping("/register")
    public void register(){;}

    @PostMapping("/register")
    public String registerOk(@Valid UserDomain user
            , BindingResult result
            , Model model
            , RedirectAttributes redirectAttrs
    ) {

        if(!result.hasFieldErrors("username") && userService.isExist(user.getUsername())) {
            result.rejectValue("username", "이미 존재하는 아이디 입니다.");
        }

        if(!result.hasFieldErrors("email") && userService.isExistEmail(user.getEmail())) {
            result.rejectValue("email", "이미 존재하는 이메일 입니다.");
        }

        // if there was an error
        if(result.hasErrors()){
            redirectAttrs.addFlashAttribute("username", user.getUsername());
            redirectAttrs.addFlashAttribute("name", user.getNickname());
            redirectAttrs.addFlashAttribute("email", user.getEmail());
            List<FieldError> errList = result.getFieldErrors();
            for(FieldError err : errList) {
                redirectAttrs.addFlashAttribute("error", err.getCode());
                break;
            }
            return "redirect:/user/register";
        }

        // if there were no errors
        String page = "/user/registerOk";
        int cnt = userService.register(user);
        model.addAttribute("result", cnt);
        return page;
    }

    @GetMapping("/cart")
    public void viewCart(Model model) {
        UserDomain user = U.getLoggedUser();
        List<CartProduct> cartProducts = cartService.getCart(user.getId());
        model.addAttribute("cartProducts", cartProducts);
    }

    @GetMapping("/address")
    public void address(Model model) {
        UserDomain user = U.getLoggedUser();
        header.importData(user, model);
        QryAddressList addressList = userService.getAddressByUser(user.getId());
        model.addAttribute("addressList", addressList.getList());
        model.addAttribute("user", user);
    }

    @GetMapping("/addresslist")
    @ResponseBody
    public QryAddressList addressList(Long user_id) {
        return userService.getAddressByUser(user_id);
    }

    @PostMapping("/address/save")
    @ResponseBody
    public void addressSave(@RequestParam("user_id") Long user_id
                            , @RequestParam("name") String name
                            , @RequestParam("address") String address
                            , @RequestParam("address_detail") String address_detail
                            , @RequestParam("postcode") String postcode
                            ) {
        AddressDomain addressDomain =
                AddressDomain.builder().user_id(user_id).address(address).name(name).address_detail(address_detail).postcode(postcode).build();
        userService.saveAddress(addressDomain);
    }

    @PostMapping("/address/delete")
    @ResponseBody
    public void addressDelete(Long id) {
        userService.deleteAddress(id);
    }

    @GetMapping("/profile")
    public void profile(Model model) {
        UserDomain user = U.getLoggedUser();
        List<CartProduct> cartProducts = cartService.getCart(user.getId());
        QryAddressList addressList = userService.getAddressByUser(user.getId());
        model.addAttribute("user", user);
        model.addAttribute("address", addressList.getList());
        model.addAttribute("cartProducts", cartProducts);
    }

    @PostMapping("/profilesave")
    public String profileSave(@Valid UserDomain userDomain, @RequestParam("passwordcheck") String password, Model model, RedirectAttributes redirectAttrs) {
        if (password == null || !passwordEncoder.matches(password, U.getLoggedUser().getPassword())) {
            redirectAttrs.addFlashAttribute("error", "비밀번호를 확인해주세요.");
            return "redirect:/user/profile";
        }
        userDomain.setId(U.getLoggedUser().getId());
        U.getLoggedUser().setEmail(userDomain.getEmail());
        U.getLoggedUser().setNickname(userDomain.getNickname());
        U.getLoggedUser().setPhone(userDomain.getPhone());
        userService.update(userDomain);
        redirectAttrs.addFlashAttribute("success", "프로필 수정 성공!");
        return "redirect:/user/profile";
    }

    @GetMapping("/findPassword")
    public void findPassword() {;}

    @GetMapping("/checkEmail")
    @ResponseBody
    public boolean checkEmail(String address) {
        return userService.isExistEmail(address);
    }

    @PostMapping("/sendMail")
    @ResponseBody
    public String sendMail(@RequestParam("address") String address) {
        MailDomain mailDomain = new MailDomain(address);
        code = mailService.mailSend(mailDomain);
        return code;
    }

    @PostMapping("/codeOk")
    public String codeOk(@RequestParam("code") String inputCode, @RequestParam("address") String address, RedirectAttributes redirectAttr, HttpSession session)  {
        if(inputCode.equals(code)) {
            UserDomain user = userService.findByEmail(address);
            session.setAttribute("codeValidationPassed", true);
            redirectAttr.addFlashAttribute("user", user);
            return "redirect:/user/resetPassword";
        } else {
            return "/user/codeNoMatch";
        }
    }

    @GetMapping("/resetPassword")
    public String showResetPage(@ModelAttribute("user") UserDomain user, Model model, RedirectAttributes redirectAttr, HttpSession session) {
        Object passed = session.getAttribute("codeValidationPassed");
        if (passed == null || !(Boolean) passed || user.getId() == null) {
            return "/user/codeNoMatch";
        }
        model.addAttribute("user", user);
        return "/user/resetPassword";
    }

    @PostMapping("/delete")
    @Transactional
    public String delete(Model model) {
        UserDomain user = U.getLoggedUser();
        System.out.println(user);
        user.setU_status("OUT");
        user.setUsername(user.getUsername() + "_inactive");
        int result = userService.delete(user);
        model.addAttribute("result", result);
        return "/user/deleteOk";
    }

    @PostMapping("/resetOk")
    @Transactional
    public String resetOk(UserDomain user, Model model) {
        int result = userService.resetPassword(user);
        model.addAttribute("result", result);
        return "/user/resetOk";
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.setValidator(new UserValidator());
    }
}
