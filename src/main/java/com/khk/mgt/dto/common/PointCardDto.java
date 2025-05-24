package com.khk.mgt.dto.common;

import com.khk.mgt.validator.annotations.CustomerId;
import com.khk.mgt.validator.annotations.PointCardId;
import com.khk.mgt.validator.annotations.ValidPointCardForCustomer;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.sql.Date;

@Getter
@Setter
@ToString
@ValidPointCardForCustomer(message = "{input.field.validation.pointcard.and.customer.match}", groups = {OnUpdate.class, OnDelete.class})
public class PointCardDto {

    @NotNull(message = "{input.field.validation.pointcard.customer.id.notblank}", groups = {OnCreate.class, OnUpdate.class, OnDelete.class})
    @CustomerId(message = "{input.field.validation.pointcard.customer.id.notfound}", groups = {OnCreate.class, OnUpdate.class, OnDelete.class})
    private Long customerId;

    @NotNull(message = "{input.field.validation.pointcard.category.type.notblank}", groups = {OnCreate.class, OnUpdate.class})
    private Long pointCardCategory;

    @NotNull(message = "{input.field.validation.pointcard.id.notblank}",groups = {OnUpdate.class,OnDelete.class})
    @PointCardId(message = "{input.field.validation.pointcard.id.notfound}", groups = {OnUpdate.class,OnDelete.class})
    private Long pointCardId;

    private String firstName;
    private String lastName;
    private String gender;
    private Date registrationDate;
    private String pointCardType;
    private Long points;

    public PointCardDto() {
    }

}
