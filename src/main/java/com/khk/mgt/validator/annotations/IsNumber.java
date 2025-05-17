package com.khk.mgt.validator.annotations;
import com.khk.mgt.validator.impl.IsNumberValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = IsNumberValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface IsNumber {
    String message() default "Value must be a valid number";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
