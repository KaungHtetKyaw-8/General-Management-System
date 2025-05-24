package com.khk.mgt.dto.common;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VendorDto extends PersonDto {

    @NotBlank(message = "{input.field.validation.vendor.company.name.notblank}",groups = {OnCreate.class, OnUpdate.class})
    private String companyName;

    public VendorDto() {
        super();
        setAddress(new AddressDto());
    }

    @Override
    public String toString() {
        return "VendorDto{" +
                "personDto" + super.toString() +
                "companyName='" + companyName + '\'' +
                '}';
    }
}
