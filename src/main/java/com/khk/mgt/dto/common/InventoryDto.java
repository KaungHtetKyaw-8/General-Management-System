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

    @NotNull(message = "{input.field.validation.inventory.vendor.id}", groups={OnCreate.class})
    private Long vendorId;
    @NotNull(message = "{input.field.validation.inventory.category.id}", groups={OnCreate.class})
    private Long categoryId;

    @NotBlank(message = "{input.field.validation.inventory.product.name}",groups = {OnCreate.class, OnUpdate.class})
    private String name;

    @NotNull(message = "{input.field.validation.inventory.product.buyprice.notblank}",groups = {OnCreate.class, OnUpdate.class})
    @NegativeOrZero(message = "{input.field.validation.inventory.product.buyprice.negative}")
    @Digits(integer = 10,fraction = 2,groups = {OnCreate.class, OnUpdate.class})
    private double buyPrice;

    @NotNull(message = "{input.field.validation.inventory.product.sellprice.notblank}",groups = {OnCreate.class, OnUpdate.class})
    @NegativeOrZero(message = "{input.field.validation.inventory.product.sellprice.negative}")
    @Digits(integer = 10,fraction = 2,groups = {OnCreate.class, OnUpdate.class})
    private double sellPrice;

    @NotNull(message = "{input.field.validation.inventory.product.count.notblank}",groups = {OnCreate.class, OnUpdate.class})
    @NegativeOrZero(message = "{input.field.validation.inventory.product.count.negative}")
    @Digits(integer = 10,fraction = 0,groups = {OnCreate.class, OnUpdate.class})
    private Long count;

    // Only Show For Update and Delete
    private String categoryName;
    private String vendorName;
    private String companyName;

    public InventoryDto() {
    }
}
