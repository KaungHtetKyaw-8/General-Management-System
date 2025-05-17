package com.khk.mgt.validator.impl;

import com.khk.mgt.service.CustomerService;
import com.khk.mgt.validator.annotations.CustomerId;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

public class CustomerIdValidator implements ConstraintValidator<CustomerId, Long> {
    @Autowired
    private CustomerService customerService;

    @Override
    public boolean isValid(Long id, ConstraintValidatorContext constraintValidatorContext) {
        if (id == null) {
            return true; // other validators like @NotBlank handle this
        }
        return !customerService.isCustomerExist(id);
    }
}
