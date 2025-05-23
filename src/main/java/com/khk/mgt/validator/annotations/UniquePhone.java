package com.khk.mgt.validator.annotations;


import com.khk.mgt.validator.impl.UniquePhoneNumberValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = UniquePhoneNumberValidator.class)
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface UniquePhone {

    String message() default "Phone Number is already in use";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
