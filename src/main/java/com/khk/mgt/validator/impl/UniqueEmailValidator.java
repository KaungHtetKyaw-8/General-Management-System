package com.khk.mgt.validator.impl;

import com.khk.mgt.service.EmployeeService;
import com.khk.mgt.validator.annotations.UniqueMail;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

public class UniqueEmailValidator implements ConstraintValidator<UniqueMail, String> {
    @Autowired
    private EmployeeService employeeService;

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        if (email == null || email.isBlank()) {
            return true; // other validators like @NotBlank handle this
        }
        return !employeeService.emailExists(email);
    }
}
