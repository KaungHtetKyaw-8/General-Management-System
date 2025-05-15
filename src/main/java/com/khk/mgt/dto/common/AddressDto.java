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

    @NotBlank(message = "Apartment must not blank.",groups = {OnCreate.class, OnUpdate.class})
    private String apartment;

    @NotBlank(message = "Street must not blank.",groups = {OnCreate.class, OnUpdate.class})
    private String street;

    @NotBlank(message = "City must not blank.",groups = {OnCreate.class, OnUpdate.class})
    private String city;

    @NotBlank(message = "Country must not blank.",groups = {OnCreate.class, OnUpdate.class})
    private String countryCode;

    public AddressDto() {
    }
}
