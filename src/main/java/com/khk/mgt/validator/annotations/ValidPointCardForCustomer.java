package com.khk.mgt.validator.annotations;

import com.khk.mgt.validator.impl.PointCardBelongsToCustomerValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PointCardBelongsToCustomerValidator.class)
@Documented
public @interface ValidPointCardForCustomer {

    String message() default "Point card does not belong to the specified customer.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
