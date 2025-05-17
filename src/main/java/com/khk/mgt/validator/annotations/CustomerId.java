package com.khk.mgt.validator.annotations;

import com.khk.mgt.validator.impl.CustomerIdValidator;
import com.khk.mgt.validator.impl.UniqueEmailValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = CustomerIdValidator.class)
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface CustomerId {
    String message() default "Customer Id not found";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
