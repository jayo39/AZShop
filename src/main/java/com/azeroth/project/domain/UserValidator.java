package com.azeroth.project.domain;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

public class UserValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return UserDomain.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        UserDomain user = (UserDomain) target;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "Username is required.");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "nickname", "Name is required.");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "Password is required.");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "phone", "Phone number is required.");

        if(!user.getPassword().equals(user.getRe_password())){
            errors.rejectValue("re_password", "Passwords do not match.");
        }
    }
}
