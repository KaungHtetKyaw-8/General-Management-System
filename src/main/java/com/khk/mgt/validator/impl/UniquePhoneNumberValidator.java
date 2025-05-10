package com.khk.mgt.validator.impl;


import com.khk.mgt.dao.PersonDao;
import com.khk.mgt.validator.annotations.UniquePhone;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

public class UniquePhoneNumberValidator implements ConstraintValidator<UniquePhone, String> {

    @Autowired
    private PersonDao personDao;

    @Override
    public boolean isValid(String phone, ConstraintValidatorContext constraintValidatorContext) {
        if (phone == null || phone.isBlank()) {
            return true; // other validators like @NotBlank handle this
        }
        return !personDao.existsByPhone(phone);
    }
}
