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
@ValidPointCardForCustomer(groups = {OnUpdate.class, OnDelete.class})
public class PointCardDto {

    @NotNull(message = "Customer ID must not null", groups = {OnCreate.class, OnUpdate.class, OnDelete.class})
    @CustomerId(groups = {OnCreate.class, OnUpdate.class, OnDelete.class})
    private Long customerId;

    @NotNull(message = "Point Card Category must not null", groups = {OnCreate.class, OnUpdate.class})
    private Long pointCardCategory;

    @NotNull(message = "Point Card ID must not null",groups = {OnUpdate.class,OnDelete.class})
    @PointCardId(groups = {OnUpdate.class,OnDelete.class})
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
