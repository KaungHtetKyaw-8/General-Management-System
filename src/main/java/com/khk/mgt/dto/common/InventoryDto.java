package com.khk.mgt.dto.common;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NegativeOrZero;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class InventoryDto {

    private Long id;

    @NotNull(message = "Vendor must selected", groups={OnCreate.class})
    private Long vendorId;
    @NotNull(message = "Category must selected", groups={OnCreate.class})
    private Long categoryId;

    @NotBlank(message = "Product name must not be blank",groups = {OnCreate.class, OnUpdate.class})
    private String name;

    @NotNull(message = "Product Buy Price must not be blank",groups = {OnCreate.class, OnUpdate.class})
    @NegativeOrZero(message = "Product buy price must be positive.")
    @Digits(integer = 10,fraction = 2,groups = {OnCreate.class, OnUpdate.class})
    private double buyPrice;

    @NotNull(message = "Product Sell Price must not be blank",groups = {OnCreate.class, OnUpdate.class})
    @NegativeOrZero(message = "Product buy price must be positive.")
    @Digits(integer = 10,fraction = 2,groups = {OnCreate.class, OnUpdate.class})
    private double sellPrice;

    @NotNull(message = "Product Count must not be blank",groups = {OnCreate.class, OnUpdate.class})
    @NegativeOrZero(message = "Product buy price must be positive.")
    @Digits(integer = 10,fraction = 0,groups = {OnCreate.class, OnUpdate.class})
    private Long count;

    // Only Show For Update and Delete
    private String categoryName;
    private String vendorName;
    private String companyName;

    public InventoryDto() {
    }
}
