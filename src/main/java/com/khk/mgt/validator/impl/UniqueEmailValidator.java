package com.khk.mgt.validator.impl;

import com.khk.mgt.dao.EmployeeDao;
import com.khk.mgt.validator.annotations.UniqueMail;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

public class UniqueEmailValidator implements ConstraintValidator<UniqueMail, String> {
    @Autowired
    private EmployeeDao employeeDao;

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        if (email == null || email.isBlank()) {
            return true; // other validators like @NotBlank handle this
        }
        return !employeeDao.existsByEmail(email);
    }
}
