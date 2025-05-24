package com.khk.mgt.dto.common;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class AddressDto {

    private Long id;

    @NotBlank(message = "{input.field.validation.address.apartment}",groups = {OnCreate.class, OnUpdate.class})
    private String apartment;

    @NotBlank(message = "{input.field.validation.address.street}",groups = {OnCreate.class, OnUpdate.class})
    private String street;

    @NotBlank(message = "{input.field.validation.address.city}",groups = {OnCreate.class, OnUpdate.class})
    private String city;

    @NotBlank(message = "{input.field.validation.address.country}",groups = {OnCreate.class, OnUpdate.class})
    private String countryCode;

    public AddressDto() {
    }
}
