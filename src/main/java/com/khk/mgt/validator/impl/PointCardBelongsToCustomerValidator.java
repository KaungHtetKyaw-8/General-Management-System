package com.khk.mgt.validator.impl;

import com.khk.mgt.dto.common.PointCardDto;
import com.khk.mgt.service.PointCardService;
import com.khk.mgt.validator.annotations.ValidPointCardForCustomer;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

public class PointCardBelongsToCustomerValidator implements ConstraintValidator<ValidPointCardForCustomer, PointCardDto> {

    @Autowired
    private PointCardService pointCardService;

    @Override
    public boolean isValid(PointCardDto dto, ConstraintValidatorContext context) {
        if (dto.getPointCardId() == null || dto.getCustomerId() == null) {
            return true; // let @NotNull handle null checks
        }

        return pointCardService.isMatchWithCustomer(dto.getPointCardId(), dto.getCustomerId());
    }
}
