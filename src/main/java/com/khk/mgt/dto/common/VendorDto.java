package com.khk.mgt.dto.common;

import com.khk.mgt.ds.PointCard;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class VendorDto extends PersonDto {

    @NotBlank(message = "Company Name must not be blank",groups = {OnCreate.class, OnUpdate.class})
    private String companyName;

    public VendorDto() {
        super();
        setAddress(new AddressDto());
    }
}
