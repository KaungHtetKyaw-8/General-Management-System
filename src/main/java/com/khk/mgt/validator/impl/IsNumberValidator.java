package com.khk.mgt.validator.impl;

import com.khk.mgt.validator.annotations.IsNumber;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.aspectj.weaver.ast.Instanceof;

public class IsNumberValidator implements ConstraintValidator<IsNumber, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isBlank()) return true; // Let @NotBlank handle null/blank

        try {
            Long.parseLong(value); // or Integer.parseInt(value), Double.parseDouble(value), etc.
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
