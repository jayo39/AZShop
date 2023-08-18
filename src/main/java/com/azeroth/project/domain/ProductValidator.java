package com.azeroth.project.domain;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

public class ProductValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return ProductDomain.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ProductDomain product = (ProductDomain) target;
        String p_name = product.getP_name();
        String main_cate = product.getMain_cate();
        String sub_cate = product.getSub_cate();
        Long price = product.getPrice();

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "p_name", "Enter a product name");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "main_cate", "Select a main category");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "sub_cate", "Select a sub category");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "price", "Enter a price");
    }
}
