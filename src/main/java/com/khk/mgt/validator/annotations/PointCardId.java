package com.khk.mgt.validator.annotations;

import com.khk.mgt.validator.impl.CustomerIdValidator;
import com.khk.mgt.validator.impl.PointCardIdValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = PointCardIdValidator.class)
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface PointCardId {
    String message() default "Point Card Id not found";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
