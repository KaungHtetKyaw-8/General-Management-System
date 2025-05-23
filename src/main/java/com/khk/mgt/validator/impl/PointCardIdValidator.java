package com.khk.mgt.validator.impl;

import com.khk.mgt.service.PointCardService;
import com.khk.mgt.validator.annotations.PointCardId;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

public class PointCardIdValidator implements ConstraintValidator<PointCardId, Long> {
    @Autowired
    private PointCardService pointCardService;

    @Override
    public boolean isValid(Long id, ConstraintValidatorContext constraintValidatorContext) {
        if (id == null) {
            return true; // other validators like @NotBlank handle this
        }
        return !pointCardService.isPointCardExist(id);
    }
}
